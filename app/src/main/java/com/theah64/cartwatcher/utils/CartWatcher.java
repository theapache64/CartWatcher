package com.theah64.cartwatcher.utils;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.theah64.retrokit.retro.RetroKit;

/**
 * Created by theapache64 on 12/9/17.
 */

public class CartWatcher extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RetroKit.init(this)
                .setRetrofitBaseURL("http://theapache64.xyz:8080/mock_api/get_json/cartwatcher/")
                .enableVersionCheck()
                .setIconModule(new FontAwesomeModule())
                .setDefaultFontPathAsRobotoRegular();
    }
}
