package com.theah64.cartwatcher.models;

/**
 * Created by theapache64 on 16/9/17.
 */

public class PriceHistory {
    private final String productId;
    private final long price;


    public PriceHistory(String productId, long price) {
        this.productId = productId;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public long getPrice() {
        return price;
    }
}
