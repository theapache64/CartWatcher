package com.theah64.retrokit.activities;

import android.support.annotation.NonNull;

import com.theah64.retrokit.R;
import com.theah64.retrokit.retro.BaseAPIResponse;
import com.theah64.retrokit.utils.ProgressManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by theapache64 on 25/8/17.
 */

public abstract class BaseDynamicActivity<DATA, APIINTERFACE> extends BaseRefreshableActivity<DATA, APIINTERFACE> {

    private Call<BaseAPIResponse<DATA>> call;

    @Override
    public void loadData() {

        if (!isSetupCalled()) {
            throw new IllegalArgumentException("You should call setup() before calling loadData()");
        }

        if (call != null) {
            call.cancel();
        }

        getProgressMan().showLoading(getLoadingMessage());

        this.call = getCall(getAPIInterface());
        this.call.enqueue(new Callback<BaseAPIResponse<DATA>>() {
            @Override
            public void onResponse(@NonNull Call<BaseAPIResponse<DATA>> call, @NonNull final Response<BaseAPIResponse<DATA>> response) {
                if (response.body() != null) {
                    if (response.body().isError()) {
                        getProgressMan().showError(ProgressManager.ERROR_TYPE_SERVER_ERROR, response.body().getMessage());
                    } else {
                        onSuccess(response.body());
                        getProgressMan().showMainView();
                    }
                } else {
                    getProgressMan().showError(ProgressManager.ERROR_TYPE_SERVER_ERROR, R.string.server_error);
                }

            }

            @Override
            public void onFailure(@NonNull Call<BaseAPIResponse<DATA>> call, Throwable t) {
                t.printStackTrace();
                if (!call.isCanceled()) {
                    getProgressMan().showError(ProgressManager.ERROR_TYPE_NETWORK_ERROR, R.string.network_error);
                }
            }
        });

    }

    protected abstract void onSuccess(BaseAPIResponse<DATA> body);

    protected abstract String getLoadingMessage();

    protected abstract APIINTERFACE getAPIInterface();

    protected abstract Call<BaseAPIResponse<DATA>> getCall(APIINTERFACE apiInterface);

    @Override
    protected void onStop() {
        super.onStop();
        if (this.call != null && !this.call.isCanceled()) {
            System.out.println("Pending call cancelled");
            this.call.cancel();
        }
    }
}
