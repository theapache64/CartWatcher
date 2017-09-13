package com.theah64.cartwatcher.lab.core;

/**
 * Created by theapache64 on 13/9/17.
 */

public abstract class BaseECommerceService {

    private final String data;
    private Product product;

    protected BaseECommerceService(String data) {
        this.data = data;
    }

    protected String getData() {
        return data;
    }

    public static BaseECommerceService getECommerceService(String productUrl, final String data) throws CartWatcherException {
        if (productUrl.contains("flipkart")) {
            return new Flipkart(data);
        } else if (productUrl.contains("amazon")) {
            return new Amazon(data);
        } else {
            throw new CartWatcherException("Product url is neither from amazon nor flipkart");
        }
    }

    public abstract Product getProduct() throws CartWatcherException;
}
