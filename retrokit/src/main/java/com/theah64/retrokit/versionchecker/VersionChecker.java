package com.theah64.retrokit.versionchecker;

import android.content.Context;

import com.theah64.retrokit.utils.CommonUtils;
import com.theah64.retrokit.utils.PreferenceUtils;

/**
 * Created by theapache64 on 12/9/17.
 */

public class VersionChecker {

    public static final String KEY_LATEST_VERSION_CODE = "latest_version_code";

    /**
     * Check if current version code less than latest version code
     */
    public static boolean isExpired(final Context context) {
        final int latestVersionCode = PreferenceUtils.getInt(KEY_LATEST_VERSION_CODE);
        return CommonUtils.getVersionCode(context) < latestVersionCode;
    }
}
