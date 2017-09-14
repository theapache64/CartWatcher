package com.theah64.cartwatcher.utils;

import com.theah64.cartwatcher.models.Product;
import com.theah64.retrokit.retro.BaseAPIResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by theapache64 on 14/9/17.
 */

public interface APIInterface {
    @GET("get_product")
    Call<BaseAPIResponse<Product>> getProduct(@Query("product_url") String productUrl);
}
