package com.theah64.retrokit.utils;

import com.theah64.retrokit.retro.BaseAPIResponse;
import com.theah64.retrokit.versionchecker.response.VersionCheckerResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by theapache64 on 12/9/17.
 */

public interface APIInterface {

    @GET("get_latest_version_details")
    Call<BaseAPIResponse<VersionCheckerResponse>> getVersionCheckResponse();


}


