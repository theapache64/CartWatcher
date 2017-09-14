package com.theah64.cartwatcher.models;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by theapache64 on 13/9/17.
 */

public class Product {

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

    Product(String id, String title, long price, String source, String imageUrl, String specialId) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.source = source;
        this.imageUrl = imageUrl;
        this.specialId = specialId;
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
