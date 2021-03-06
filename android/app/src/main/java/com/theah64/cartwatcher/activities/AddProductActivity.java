package com.theah64.cartwatcher.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.theah64.cartwatcher.BuildConfig;
import com.theah64.cartwatcher.R;
import com.theah64.cartwatcher.database.PriceHistories;
import com.theah64.cartwatcher.database.Products;
import com.theah64.cartwatcher.models.PriceHistory;
import com.theah64.cartwatcher.models.Product;
import com.theah64.cartwatcher.responses.GetProductResponse;
import com.theah64.cartwatcher.services.PriceUpdaterService;
import com.theah64.cartwatcher.utils.APIInterface;
import com.theah64.retrokit.activities.BaseAppCompatActivity;
import com.theah64.retrokit.exceptions.CustomRuntimeException;
import com.theah64.retrokit.retro.BaseAPIResponse;
import com.theah64.retrokit.retro.CustomRetrofitCallback;
import com.theah64.retrokit.retro.RetrofitClient;
import com.theah64.retrokit.utils.DialogUtils;
import com.theah64.retrokit.widgets.ValidTextInputLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddProductActivity extends BaseAppCompatActivity {

    public static final int RQ_CODE = 45;
    @BindView(R.id.vtilProductURL)
    ValidTextInputLayout vtilProductURL;

    @BindView(R.id.vtilHitInterval)
    ValidTextInputLayout vtilHitInterval;

    @BindView(R.id.spIntervalTypes)
    Spinner spIntervalTypes;

    private static final List<String> INTERVAL_TYPES = new ArrayList<>();

    static {
        INTERVAL_TYPES.add(Product.INTERVAL_TYPE_MINUTE);
        INTERVAL_TYPES.add(Product.INTERVAL_TYPE_HOUR);
        INTERVAL_TYPES.add(Product.INTERVAL_TYPE_DAY);
        INTERVAL_TYPES.add(Product.INTERVAL_TYPE_WEEK);
        INTERVAL_TYPES.add(Product.INTERVAL_TYPE_MONTH);
        INTERVAL_TYPES.add(Product.INTERVAL_TYPE_YEAR);
    }

    private ValidTextInputLayout.InputValidator inputValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewWithButterKnife(R.layout.activity_add_product);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        enableBackNavigation();

        //Setting debug value
        if (BuildConfig.DEBUG) {
            vtilProductURL.setText("http://google.com");
        }

        //Setting interval types
        ArrayAdapter<String> intervalTypesAdapter = new ArrayAdapter<String>(AddProductActivity.this, android.R.layout.simple_spinner_item, INTERVAL_TYPES);
        intervalTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spIntervalTypes.setAdapter(intervalTypesAdapter);

        //Setting default values
        vtilHitInterval.setText("1");
        spIntervalTypes.setSelection(INTERVAL_TYPES.indexOf(Product.INTERVAL_TYPE_MINUTE));

        inputValidator = new ValidTextInputLayout.InputValidator(vtilProductURL, vtilHitInterval);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final String clipboardProductUrl = getValidRecentProductURLFromClipboard();
        System.out.println("Clipboard url is " + clipboardProductUrl);

        if (clipboardProductUrl != null) {
            //We've one product url in clipboard
            getDialogUtils().getClosedQuestionDialog(getString(R.string.You_ve_one_url_in_cboard), new DialogUtils.ClosedQuestionCallback() {
                @Override
                public void onYes() {
                    vtilProductURL.setText(clipboardProductUrl);
                }

                @Override
                public void onNo() {

                }
            }).show();
        }
    }

    @OnClick(R.id.fabAddProduct)
    public void onAddProductClicked() {

        if (inputValidator.isAllValid()) {
            //All inputs are valid

            final String productUrl = vtilProductURL.getString();

            //Checking if the url is valid
            if (URLUtil.isValidUrl(productUrl)) {

                final MaterialDialog progressDialog = getDialogUtils().getProgressDialog(R.string.Adding_product);
                progressDialog.show();

                //Valid url, so making API request
                RetrofitClient.getClient().create(APIInterface.class).getProduct(productUrl).enqueue(new CustomRetrofitCallback<BaseAPIResponse<GetProductResponse>, GetProductResponse>() {
                    @Override
                    protected void onSuccess(GetProductResponse data) {
                        progressDialog.dismiss();

                        System.out.println("Product loaded: " + data.getProduct());

                        final Product product = data.getProduct();
                        final Products pTable = Products.getInstance(AddProductActivity.this);

                        final String oldProductId = pTable.get(Products.COLUMN_SPECIAL_ID, product.getSpecialId(), Products.COLUMN_SOURCE, product.getSource(), Products.COLUMN_ID);

                        if (oldProductId == null) {

                            final long hitInterval = Long.parseLong(vtilHitInterval.getString());
                            final String hitIntervalType = spIntervalTypes.getSelectedItem().toString();

                            product.setHitInterval(hitInterval);
                            product.setHitIntervalType(hitIntervalType);
                            product.setHitIntervalInMillis(hitInterval, hitIntervalType);
                            product.setHitActive(true);

                            final long lastHitInMillis = System.currentTimeMillis();
                            final long nextHitInMillis = System.currentTimeMillis() + product.getHitIntervalInMillis();

                            product.setLastHitInMillis(lastHitInMillis);
                            product.setNextHitInMillis(nextHitInMillis);

                            try {
                                final String newProductId = String.valueOf(pTable.add(product));
                                product.setId(newProductId);

                                //Adding first price history
                                PriceHistories.getInstance(AddProductActivity.this).add(new PriceHistory(newProductId, product.getCurrentPrice()));

                                //Setting result, so that can the products activity can load this item into recyclerview
                                final Intent newProductIntent = new Intent();
                                newProductIntent.putExtra(Product.KEY, product);
                                setResult(RESULT_OK, newProductIntent);

                                Toast.makeText(AddProductActivity.this, "New product added", Toast.LENGTH_SHORT).show();

                                //Building alarm manager
                                final Intent alarmIntent = new Intent(AddProductActivity.this, PriceUpdaterService.class);
                                alarmIntent.putExtra(Products.COLUMN_ID, newProductId);
                                final PendingIntent alarmPendingIntent = PendingIntent.getService(AddProductActivity.this, 0, alarmIntent, 0);
                                final AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                am.cancel(alarmPendingIntent); //cancelling existing alarm
                                System.out.println("Hit Interval is " + product.getHitIntervalInMillis());
                                am.setRepeating(AlarmManager.RTC_WAKEUP, nextHitInMillis, product.getHitIntervalInMillis(), alarmPendingIntent);
                                finish();

                            } catch (CustomRuntimeException e) {
                                e.printStackTrace();
                                getDialogUtils().showErrorDialog(e.getMessage());
                            }

                        } else {

                            //We've already the product added, so add the price variation

                            //Adding price history
                            PriceHistories.getInstance(AddProductActivity.this).addPriceIfChanged(new PriceHistory(oldProductId, product.getCurrentPrice()));

                            //Product exist in db
                            getDialogUtils().showErrorDialog(R.string.Product_exists);
                        }
                    }

                    @Override
                    protected void onFailure(String message) {
                        progressDialog.dismiss();
                        getDialogUtils().showErrorDialog(message);

                    }
                });


            } else {
                //Invalid URL
                vtilProductURL.setError(getString(R.string.Invalid_URL));
                Toast.makeText(this, R.string.Invalid_URL, Toast.LENGTH_SHORT).show();
            }

        }
    }

    public String getValidRecentProductURLFromClipboard() {

        String clipboardData = null;

        final ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData primaryClip = clipboardManager.getPrimaryClip();
        if (primaryClip != null) {
            final ClipData.Item clipData = primaryClip.getItemAt(0);
            if (clipData != null) {
                clipboardData = clipData.getText().toString();
            }

            if (clipboardData != null) {
                if (clipboardData.contains("amazon.com") || clipboardData.contains("flipkart.com")) {
                    return clipboardData;
                }
            }
        }

        return null;
    }

}
