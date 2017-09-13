package com.theah64.cartwatcher.lab.core;

/**
 * Created by theapache64 on 13/9/17.
 */

public class Product {

    private final String title;
    private final long price;

    public Product(String title, long price) {
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public long getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}
