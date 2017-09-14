package com.theah64.cartwatcher.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.theah64.cartwatcher.R;
import com.theah64.cartwatcher.database.Products;
import com.theah64.cartwatcher.exceptions.CartWatcherSQLException;
import com.theah64.cartwatcher.models.Product;
import com.theah64.cartwatcher.responses.GetProductResponse;
import com.theah64.cartwatcher.utils.APIInterface;
import com.theah64.retrokit.activities.BaseAppCompatActivity;
import com.theah64.retrokit.retro.BaseAPIResponse;
import com.theah64.retrokit.retro.CustomRetrofitCallback;
import com.theah64.retrokit.retro.RetrofitClient;

public class MainActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Products products = Products.getInstance(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String lastClipboardData = getValidRecentProductURLFromClipboard();

                //Getting url
                new MaterialDialog.Builder(MainActivity.this)
                        .title(R.string.Add_product)
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .autoDismiss(false)
                        .input(getString(R.string.Paste_product_URL_here), lastClipboardData, false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull final MaterialDialog dialog, CharSequence input) {

                                final String productUrl = input.toString();

                                if (URLUtil.isValidUrl(productUrl)) {


                                    //Valid url
                                    RetrofitClient.getClient().create(APIInterface.class).getProduct(productUrl).enqueue(new CustomRetrofitCallback<BaseAPIResponse<GetProductResponse>, GetProductResponse>() {
                                        @Override
                                        protected void onSuccess(GetProductResponse data) {
                                            System.out.println("Product loaded: " + data.getProduct());
                                            dialog.dismiss();

                                            final Product product = data.getProduct();


                                            if (!products.exist(product)) {

                                                //Ask interval and interval type
                                                

                                                try {
                                                    products.add(product);
                                                } catch (CartWatcherSQLException e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                //Product exist in db
                                                Toast.makeText(MainActivity.this, R.string.Product_exists, Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        protected void onFailure(String message) {
                                            System.out.println("Product failed to load: " + message);
                                        }
                                    });

                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(MainActivity.this, R.string.Invalid_URL, Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .build().show();

            }
        });

    }

    private static final String X = MainActivity.class.getSimpleName();

    public String getValidRecentProductURLFromClipboard() {

        String clipboardData = null;

        final ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        final ClipData.Item clipData = clipboardManager.getPrimaryClip().getItemAt(0);
        if (clipData != null) {
            clipboardData = clipData.getText().toString();
        }

        if (clipboardData != null) {
            if (clipboardData.contains("amazon.com") || clipboardData.contains("flipkart.com")) {
                return clipboardData;
            }
        }

        return null;
    }
}
