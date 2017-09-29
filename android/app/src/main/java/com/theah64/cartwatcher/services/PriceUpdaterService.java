package com.theah64.cartwatcher.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.theah64.cartwatcher.R;
import com.theah64.cartwatcher.database.PriceHistories;
import com.theah64.cartwatcher.database.Products;
import com.theah64.cartwatcher.models.PriceHistory;
import com.theah64.cartwatcher.models.Product;
import com.theah64.cartwatcher.responses.GetProductResponse;
import com.theah64.cartwatcher.utils.APIInterface;
import com.theah64.cartwatcher.utils.CartWatcher;
import com.theah64.retrokit.retro.BaseAPIResponse;
import com.theah64.retrokit.retro.CustomRetrofitCallback;
import com.theah64.retrokit.retro.RetrofitClient;

public class PriceUpdaterService extends IntentService {


    public PriceUpdaterService() {
        super("PriceUpdaterService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        System.out.println("Price updater service called");

        if (intent != null) {

            final Context context = PriceUpdaterService.this;

            //Product
            final String productId = (String) intent.getSerializableExtra(Products.COLUMN_ID);
            final Products productsTable = Products.getInstance(context);
            final Product product = productsTable.get(Products.COLUMN_ID, productId);

            RetrofitClient.getClient().create(APIInterface.class).getProduct(product.getProductUrl()).enqueue(new CustomRetrofitCallback<BaseAPIResponse<GetProductResponse>, GetProductResponse>() {
                @Override
                protected void onSuccess(GetProductResponse data) {

                    //Updating product next hit time
                    final long nextHitInMillis = System.currentTimeMillis() + product.getHitIntervalInMillis();
                    product.setNextHitInMillis(nextHitInMillis);

                    //Updating product hit time
                    productsTable.update(product);

                    //Adding value to db
                    final int priceChange = PriceHistories.getInstance(PriceUpdaterService.this).addPriceIfChanged(new PriceHistory(productId, data.getProduct().getCurrentPrice()));

                    if (CartWatcher.getCallback() != null) {
                        CartWatcher.getCallback().onProductUpdated(product.getId());
                    }

                    if (priceChange == PriceHistories.PRICE_INCREASED) {
                        //Price increased, so showing price hike notification
                        showNotification(R.string.Price_increased, getString(R.string.s_price_increased_to_l, product.getTitle(), data.getProduct().getCurrentPrice()));
                    } else if (priceChange == PriceHistories.PRICE_DECREASED) {
                        //Price decreased
                    }
                }

                @Override
                protected void onFailure(String message) {

                }
            });

        } else {
            System.out.println("Intent is null");
        }
    }
}



