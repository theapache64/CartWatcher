package com.theah64.cartwatcher;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.theah64.cartwatcher.database.PriceHistories;
import com.theah64.cartwatcher.database.Products;
import com.theah64.cartwatcher.models.PriceHistory;
import com.theah64.cartwatcher.models.Product;
import com.theah64.cartwatcher.responses.GetProductResponse;
import com.theah64.cartwatcher.utils.APIInterface;
import com.theah64.retrokit.retro.BaseAPIResponse;
import com.theah64.retrokit.retro.CustomRetrofitCallback;
import com.theah64.retrokit.retro.RetrofitClient;

public class PriceUpdaterService extends IntentService {


    public PriceUpdaterService() {
        super("PriceUpdaterService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {

            //Product
            final Product product = (Product) intent.getSerializableExtra(Products.COLUMN_ID);
            RetrofitClient.getClient().create(APIInterface.class).getProduct(product.getProductUrl()).enqueue(new CustomRetrofitCallback<BaseAPIResponse<GetProductResponse>, GetProductResponse>() {
                @Override
                protected void onSuccess(GetProductResponse data) {

                    //Adding value to db
                    PriceHistories.getInstance(PriceUpdaterService.this).add(new PriceHistory(product.getId(), data.getProduct().getCurrentPrice()));

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



