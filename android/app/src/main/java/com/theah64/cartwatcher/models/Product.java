package com.theah64.cartwatcher.models;

import com.google.gson.annotations.SerializedName;
import com.theah64.cartwatcher.database.Products;
import com.theah64.retrokit.database.CustomCursor;
import com.theah64.retrokit.utils.TimeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by theapache64 on 13/9/17.
 */

public class Product implements Serializable {

    //Hit longerval types
    public static final String INTERVAL_TYPE_MINUTE = "MINUTE";
    public static final String INTERVAL_TYPE_HOUR = "HOUR";
    public static final String INTERVAL_TYPE_DAY = "DAY";
    public static final String INTERVAL_TYPE_WEEK = "WEEK";
    public static final String INTERVAL_TYPE_MONTH = "MONTH";
    public static final String INTERVAL_TYPE_YEAR = "YEAR";

    static final String SOURCE_AMAZON = "amazon";
    static final String SOURCE_FLIPKART = "flipkart";
    public static final String KEY = "product";

    private String id;

    @SerializedName("title")
    private final String title;
    @SerializedName("price")
    private final long currentPrice;
    @SerializedName("source")
    private final String source;
    @SerializedName("image_url")
    private final String imageUrl;
    @SerializedName("special_id")
    private final String specialId;
    @SerializedName("product_url")
    private final String productUrl;

    private long hitInterval;
    private long hitIntervalInMillis, lastHitInMillis, nextHitInMillis;
    private String hitIntervalType;

    private final long recentPrice;
    private boolean isHitActive;


    Product(String id, String title, long currentPrice, long recentPrice, String source, String imageUrl, String specialId, String productUrl, long hitInterval, String hitIntervalType, boolean isHitActive, long lastHitInMillis, long nextHitInMillis) {
        this.id = id;
        this.title = title;
        this.currentPrice = currentPrice;
        this.recentPrice = recentPrice;
        this.source = source;
        this.imageUrl = imageUrl;
        this.specialId = specialId;
        this.productUrl = productUrl;
        this.hitInterval = hitInterval;
        this.hitIntervalType = hitIntervalType;
        this.isHitActive = isHitActive;
        this.lastHitInMillis = lastHitInMillis;
        this.nextHitInMillis = nextHitInMillis;

        setHitIntervalInMillis(hitInterval, hitIntervalType);
    }


    public long getLastHitInMillis() {
        return lastHitInMillis;
    }

    public void setLastHitInMillis(long lastHitInMillis) {
        this.lastHitInMillis = lastHitInMillis;
    }

    public long getNextHitInMillis() {
        return nextHitInMillis;
    }

    public void setNextHitInMillis(long nextHitInMillis) {
        this.nextHitInMillis = nextHitInMillis;
    }

    public void setHitActive(boolean hitActive) {
        isHitActive = hitActive;
    }

    public void setHitInterval(long hitInterval) {
        this.hitInterval = hitInterval;
    }


    public void setHitIntervalType(String hitIntervalType) {
        this.hitIntervalType = hitIntervalType;
    }

    public void setHitIntervalInMillis(long hitIntervalInMillis) {
        this.hitIntervalInMillis = hitIntervalInMillis;
    }

    public long getHitInterval() {
        return hitInterval;
    }

    public long getHitIntervalInMillis() {
        return hitIntervalInMillis;
    }

    public String getHitIntervalType() {
        return hitIntervalType;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSpecialId() {
        return specialId;
    }

    public String getTitle() {
        return title;
    }

    public long getCurrentPrice() {
        return currentPrice;
    }

    public String getSource() {
        return source;
    }


    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", currentPrice=" + currentPrice +
                ", source='" + source + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", specialId='" + specialId + '\'' +
                ", productUrl='" + productUrl + '\'' +
                ", hitInterval=" + hitInterval +
                ", hitIntervalInMillis=" + hitIntervalInMillis +
                ", hitIntervalType='" + hitIntervalType + '\'' +
                ", recentPrice=" + recentPrice +
                ", isHitActive=" + isHitActive +
                '}';
    }

    public JSONObject toJSONObject() throws JSONException {
        final JSONObject joProduct = new JSONObject();
        joProduct.put("title", title);
        joProduct.put("currentPrice", currentPrice);
        joProduct.put("source", source);
        return joProduct;
    }

    public void setHitIntervalInMillis(long interval, String intervalType) {
        long hitIntervalInMillis = 0;

        switch (intervalType) {

            case INTERVAL_TYPE_MINUTE:
                hitIntervalInMillis = (interval * 1000) * 60;
                break;

            case INTERVAL_TYPE_HOUR:
                hitIntervalInMillis = ((interval * 1000) * 60) * 60;
                break;

            case INTERVAL_TYPE_DAY:
                hitIntervalInMillis = (((interval * 1000) * 60) * 60) * 24;
                break;

            case INTERVAL_TYPE_WEEK:
                hitIntervalInMillis = ((((interval * 1000) * 60) * 60) * 24) * 7;
                break;

            case INTERVAL_TYPE_MONTH:
                hitIntervalInMillis = (((((interval * 1000) * 60) * 60) * 24) * 7) * 30;
                break;

            case INTERVAL_TYPE_YEAR:
                hitIntervalInMillis = ((((((interval * 1000) * 60) * 60) * 24) * 7) * 30) * 12;
                break;

            default:
                throw new IllegalArgumentException("Invalid interval type " + intervalType);
        }

        System.out.println(interval + " " + intervalType + " in milliseconds is " + hitIntervalInMillis);
        this.hitIntervalInMillis = hitIntervalInMillis;
    }

    public long getPriceFluctuated() {
        return recentPrice != 0 ? currentPrice - recentPrice : 0;
    }

    public int getNextHitProgress() {
        //Calc hit progress from current time and last hit and next hit
        return TimeUtils.getPercentageFinished(lastHitInMillis, System.currentTimeMillis(), nextHitInMillis);
    }

    public boolean isHitActive() {
        return isHitActive;
    }

    public static Product parse(CustomCursor cursor) {
        return new Product(
                cursor.getStringByColumnIndex(Products.COLUMN_ID),
                cursor.getStringByColumnIndex(Products.COLUMN_TITLE),
                cursor.getLongByColumnIndex(Products.COLUMN_AS_CURRENT_PRICE),
                cursor.getLongByColumnIndex(Products.COLUMN_AS_RECENT_PRICE),
                cursor.getStringByColumnIndex(Products.COLUMN_SOURCE),
                cursor.getStringByColumnIndex(Products.COLUMN_IMAGE_URL),
                cursor.getStringByColumnIndex(Products.COLUMN_SPECIAL_ID),
                cursor.getStringByColumnIndex(Products.COLUMN_PRODUCT_URL),
                cursor.getLongByColumnIndex(Products.COLUMN_HIT_INTERVAL),
                cursor.getStringByColumnIndex(Products.COLUMN_HIT_INTERVAL_TYPE),
                cursor.getBooleanByColumnIndex(Products.COLUMN_IS_HIT_ACTIVE),
                cursor.getLongByColumnIndex(Products.COLUMN_AS_LAST_HIT_IN_MILLIS),
                cursor.getLongByColumnIndex(Products.COLUMN_NEXT_HIT_IN_MILLIS));
    }

    public void setId(String id) {
        this.id = id;
    }
}
