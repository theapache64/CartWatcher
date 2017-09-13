package com.theah64.retrokit.retro;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by theapache64 on 25/8/17.
 */

public class RetrofitClient {

    //private static final String BASE_URL = "http://theapache64.xyz:8080/mock_api/get_json/retrokitexample/";
    private static Retrofit retrofit = null;

    private static String baseUrl;

    public static void setBaseUrl(String baseUrl) {
        RetrofitClient.baseUrl = baseUrl;
    }

    public static Retrofit getClient() {

        if (baseUrl == null) {
            throw new IllegalArgumentException("Call RetroKit.init() from your Application class");
        }

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }


}
