package com.theah64.retrokit.activities;

import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.theah64.retrokit.R;
import com.theah64.retrokit.retro.BaseAPIResponse;
import com.theah64.retrokit.retro.RetroKit;
import com.theah64.retrokit.retro.RetrofitClient;
import com.theah64.retrokit.utils.APIInterface;
import com.theah64.retrokit.utils.CommonUtils;
import com.theah64.retrokit.utils.PreferenceUtils;
import com.theah64.retrokit.versionchecker.VersionChecker;
import com.theah64.retrokit.versionchecker.response.VersionCheckerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by theapache64 on 12/9/17.
 */

public abstract class BaseVersionCheckerSplashActivity extends BaseSplashActivity {

    @Override
    public void onSplashFinished() {

        //Checking version count for next start
        if (RetroKit.getInstance().isVersionCheck()) {

            final int currentVersionCode = CommonUtils.getVersionCode(this);

            //Launching version code synchronizer
            RetrofitClient.getClient().create(APIInterface.class).getVersionCheckResponse().enqueue(new Callback<BaseAPIResponse<VersionCheckerResponse>>() {
                @Override
                public void onResponse(@NonNull Call<BaseAPIResponse<VersionCheckerResponse>> call, @NonNull Response<BaseAPIResponse<VersionCheckerResponse>> response) {

                    final BaseAPIResponse<VersionCheckerResponse> versionCheckerResponse = response.body();

                    if (versionCheckerResponse != null && !versionCheckerResponse.isError()) {
                        System.out.println("New version code is " + versionCheckerResponse.getData().getLatestVersionCode() + ", Current version is " + currentVersionCode);
                        PreferenceUtils.saveInt(VersionChecker.KEY_LATEST_VERSION_CODE, versionCheckerResponse.getData().getLatestVersionCode());
                    }

                }

                @Override
                public void onFailure(@NonNull Call<BaseAPIResponse<VersionCheckerResponse>> call, @NonNull Throwable t) {

                }
            });

        }

        if (VersionChecker.isExpired(this)) {

            new MaterialDialog.Builder(this)
                    .title(R.string.Old_version)
                    .positiveText(R.string.UPDATE)
                    .negativeText(R.string.EXIT)
                    .cancelable(false)
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            finish();
                        }
                    })
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            CommonUtils.openPlayStore(BaseVersionCheckerSplashActivity.this);
                            finish();
                        }
                    })
                    .content(R.string.Looks_like_theres_new_version_available)
                    .build()
                    .show();

        } else {
            onValidVersionSplashFinished();
        }

    }

    protected abstract void onValidVersionSplashFinished();
}
