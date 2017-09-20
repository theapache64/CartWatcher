package com.theah64.cartwatcher.utils;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.theah64.bugmailer.core.BugMailer;
import com.theah64.bugmailer.core.BugMailerConfig;
import com.theah64.bugmailer.exceptions.BugMailerException;
import com.theah64.retrokit.retro.RetroKit;

/**
 * Created by theapache64 on 12/9/17.
 */

public class CartWatcher extends Application {

    private static PriceUpdaterCallback callback;


    public static PriceUpdaterCallback getCallback() {
        return callback;
    }

    public static void setCallback(PriceUpdaterCallback callback) {
        System.out.println(callback == null ? "Product updater callback UNSUBSCRIBED" : "Product updater callback SUBSCRIBED");
        CartWatcher.callback = callback;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        RetroKit.init(this)
                //.setRetrofitBaseURL("http://theapache64.xyz:8080/cartwatcher/v1/")
                .setRetrofitBaseURL("http://theapache64.xyz:8080/mock_api/get_json/cartwatcher/")
                .enableVersionCheck()
                .enableNetworkLog()
                .enableImageLoader()
                .setIconModule(new FontAwesomeModule())
                .setDefaultFontPathAsRobotoRegular();

        try {
            BugMailer.init(this, new BugMailerConfig("theapache64@gmail.com"));
        } catch (BugMailerException e) {
            e.printStackTrace();
        }
    }
}
