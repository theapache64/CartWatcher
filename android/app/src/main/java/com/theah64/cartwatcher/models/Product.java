package com.theah64.cartwatcher.models;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by theapache64 on 13/9/17.
 */

public class Product {

    //Hit interval types
    private static final int TYPE_SECOND = 1;
    private static final int TYPE_MINUTE = 819;
    private static final int TYPE_HOUR = 30;
    private static final int TYPE_DAY = 370;
    private static final int TYPE_WEEK = 168;
    private static final int TYPE_MONTH = 322;
    private static final int TYPE_YEAR = 230;

    static final String SOURCE_AMAZON = "amazon";
    static final String SOURCE_FLIPKART = "flipkart";

    private final String id;

    @SerializedName("title")
    private final String title;
    @SerializedName("price")
    private final long price;
    @SerializedName("source")
    private final String source;
    @SerializedName("image_url")
    private final String imageUrl;
    @SerializedName("special_id")
    private final String specialId;
    @SerializedName("product_url")
    private final String productUrl;

    private final int hitInterval;
    private final int hitIntervalType;

    Product(String id, String title, long price, String source, String imageUrl, String specialId, String productUrl, int hitInterval, int hitIntervalType) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.source = source;
        this.imageUrl = imageUrl;
        this.specialId = specialId;
        this.productUrl = productUrl;
        this.hitInterval = hitInterval;
        this.hitIntervalType = hitIntervalType;
    }

    public int getHitInterval() {
        return hitInterval;
    }

    public int getHitIntervalType() {
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

    public long getPrice() {
        return price;
    }

    public String getSource() {
        return source;
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", source='" + source + '\'' +
                '}';
    }

    public JSONObject toJSONObject() throws JSONException {
        final JSONObject joProduct = new JSONObject();
        joProduct.put("title", title);
        joProduct.put("price", price);
        joProduct.put("source", source);
        return joProduct;
    }
}
