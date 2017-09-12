package com.theah64.retrokit.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.theah64.retrokit.R;


/**
 * Created by theapache64 on 20/8/17.
 */

public abstract class BaseSplashActivity extends BaseAppCompatActivity {

    public abstract void onSplashStart();

    public abstract long getSplashDuration();

    public abstract void onSplashFinished();

    @DrawableRes
    public abstract int getLogo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ((ImageView) findViewById(R.id.ivSplashLogo)).setImageResource(getLogo());

        onSplashStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onSplashFinished();
            }
        }, getSplashDuration());

    }
}
