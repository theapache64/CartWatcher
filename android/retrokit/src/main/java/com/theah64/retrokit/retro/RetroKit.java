package com.theah64.retrokit.retro;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.TypedValue;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
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
    private final Context context;

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
        this.context = context;
        this.defaultProgressIndicator = new BallPulseSyncIndicator();
        this.defaultProgressIndicatorColor = fetchAccentColor(context);
        this.isDebug = false;

        PreferenceUtils.init(context);
    }

    private static int fetchAccentColor(Context mContext) {
        TypedValue typedValue = new TypedValue();
        TypedArray a = mContext.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent});
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
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

    public RetroKit addIconModule(IconFontDescriptor descriptor) {
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

    public RetroKit enableImageLoader() {
        initImageLoader(context);
        return this;
    }

    private static void initImageLoader(final Context context) {

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();

        final DisplayImageOptions defaultImageOption = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .showImageOnLoading(R.drawable.img_placeholder)
                .showImageOnFail(R.drawable.img_placeholder)
                .showImageForEmptyUri(R.drawable.img_placeholder)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        config.defaultDisplayImageOptions(defaultImageOption);

        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(100 * 1024 * 1024); // 100 MiB
        config.memoryCacheSize(50 * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        //config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
}
