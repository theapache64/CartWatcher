package com.theah64.cartwatcher;

import com.afollestad.materialdialogs.MaterialDialog;
import com.theah64.retrokit.activities.BaseSplashActivity;

/**
 * Created by theapache64 on 11/9/17.
 */

public class SplashActivity extends BaseSplashActivity {

    @Override
    public void onSplashStart() {

    }

    @Override
    public long getSplashDuration() {
        return 2000;
    }

    @Override
    public void onSplashFinished() {
        new MaterialDialog.Builder(this)
                .positiveText("OK")
                .build()
                .show();
    }

    @Override
    public int getLogo() {
        return R.drawable.ic_add_white_24dp;
    }
}
