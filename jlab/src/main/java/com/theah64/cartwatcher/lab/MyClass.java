package com.theah64.cartwatcher.lab;

import com.theah64.cartwatcher.lab.core.BaseECommerceService;
import com.theah64.cartwatcher.lab.core.CartWatcherException;
import com.theah64.cartwatcher.lab.core.Product;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//<span id="priceblock_
//<span id="productTitle"


public class MyClass {
    public static void main(String[] args) throws IOException {

        final String productUrl = "https://www.amazon.in/Moto-Plus-Lunar-Grey-64GB/dp/B071HWTHPH?tag=googinhydr18418-21&tag=googinkenshoo-21&ascsubtag=70c55133-d4bd-4a45-ba5f-c36f0f11bae1";
        new OkHttpClient().newCall(new Request.Builder().url(productUrl).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String productResponse = response.body().string();

                try {
                    final BaseECommerceService eCommerceService = BaseECommerceService.getECommerceService(productUrl, productResponse);
                    final Product product = eCommerceService.getProduct();
                    System.out.println(product);

                } catch (CartWatcherException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
