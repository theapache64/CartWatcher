package com.theah64.cartwatcher.server.core;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by theapache64 on 13/9/17.
 */

public class Product {

    static final String SOURCE_AMAZON = "amazon";
    static final String SOURCE_FLIPKART = "flipkart";
    private final String title;
    private final long price;
    private final String source;

    Product(String title, long price, String source) {
        this.title = title;
        this.price = price;
        this.source = source;
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
