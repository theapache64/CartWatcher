package com.theah64.cartwatcher.activities;

import android.content.Intent;

import com.theah64.cartwatcher.R;
import com.theah64.retrokit.activities.BaseVersionCheckerSplashActivity;

/**
 * Created by theapache64 on 11/9/17.
 */

public class SplashActivity extends BaseVersionCheckerSplashActivity {

    @Override
    public void onSplashStart() {

    }

    @Override
    public long getSplashDuration() {
        return 2000;
    }


    @Override
    public float getTextLogoSize() {
        return 28;
    }

    @Override
    public int getTextLogo() {
        return R.string.app_name;
    }

    @Override
    protected void onValidVersionSplashFinished() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
