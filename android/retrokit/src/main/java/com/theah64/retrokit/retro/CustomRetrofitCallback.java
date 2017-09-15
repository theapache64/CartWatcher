package com.theah64.retrokit.retro;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by theapache64 on 14/9/17.
 */

public abstract class CustomRetrofitCallback<T extends BaseAPIResponse, D> implements Callback<T> {


    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        final BaseAPIResponse<D> resp = response.body();
        if (resp.isError()) {
            onFailure(resp.getMessage());
        } else {
            onSuccess(resp.getData());
        }
    }

    protected abstract void onSuccess(D data);


    protected abstract void onFailure(String message);

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        t.printStackTrace();
        onFailure("Network error");
    }
}
