package com.theah64.retrokit.retro;

import android.content.Context;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import com.theah64.retrokit.R;
import com.theah64.retrokit.utils.PreferenceUtils;
import com.wang.avi.Indicator;
import com.wang.avi.indicators.BallPulseSyncIndicator;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by theapache64 on 25/8/17.
 */

public class RetroKit {

    private static RetroKit instance;

    private static final String FONT_ROBOTO_REGULAR = "fonts/Roboto-Regular.ttf";
    private static final String FONT_ROBOTO_BOLD = "fonts/Roboto-Bold.ttf";
    private static final String FONT_ROBOTO_MEDIUM = "fonts/Roboto-Medium.ttf";
    public static final String FONT_PACIFICO = "fonts/Pacifico.ttf";

    private Indicator defaultProgressIndicator;
    private int defaultProgressIndicatorColor;
    private boolean isDebug;
    private boolean versionCheck;
    private boolean logNetwork;

    public static RetroKit init(final Context context) {
        if (instance == null) {
            instance = new RetroKit(context);
        }
        return instance;
    }

    public static RetroKit getInstance() {
        return instance;
    }

    private RetroKit(final Context context) {
        this.defaultProgressIndicator = new BallPulseSyncIndicator();
        this.defaultProgressIndicatorColor = android.support.design.R.attr.colorPrimary;
        this.isDebug = false;

        PreferenceUtils.init(context);
    }

    public boolean isDebug() {
        return isDebug;
    }

    public RetroKit setDebug(boolean debug) {
        isDebug = debug;
        return this;
    }

    public RetroKit setRetrofitBaseURL(String baseUrl) {
        RetrofitClient.setBaseUrl(baseUrl);
        return this;
    }

    public RetroKit setIconModule(IconFontDescriptor descriptor) {
        Iconify.with(descriptor);
        return this;
    }

    public RetroKit setDefaultProgressIndicator(Indicator defaultProgressIndicator) {
        this.defaultProgressIndicator = defaultProgressIndicator;
        return this;
    }

    public RetroKit setDefaultProgressIndicatorColor(int defaultProgressIndicatorColor) {
        this.defaultProgressIndicatorColor = defaultProgressIndicatorColor;
        return this;
    }

    public Indicator getDefaultProgressIndicator() {
        return defaultProgressIndicator;
    }

    public int getDefaultProgressIndicatorColor() {
        return defaultProgressIndicatorColor;
    }

    public RetroKit setDefaultFontPathAsRobotoRegular() {
        setDefaultFontPath(FONT_ROBOTO_REGULAR);
        return this;
    }

    public RetroKit setDefaultFontPath(String defaultAppFont) {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(defaultAppFont)
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        return this;
    }


    public boolean isVersionCheck() {
        return versionCheck;
    }

    public RetroKit enableVersionCheck() {
        this.versionCheck = true;
        return this;
    }

    public RetroKit enableNetworkLog() {
        this.logNetwork = true;
        return this;
    }

    public boolean isLogNetwork() {
        return logNetwork;
    }
}
