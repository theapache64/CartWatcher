package com.theah64.cartwatcher.database;

import android.content.ContentValues;
import android.content.Context;

import com.theah64.cartwatcher.exceptions.CartWatcherSQLException;
import com.theah64.cartwatcher.models.Product;

/**
 * Created by theapache64 on 14/9/17.
 */

public class Products extends BaseTable<Product> {

    private static final String COLUMN_SPECIAL_ID = "special_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_SOURCE = "source";
    private static final String COLUMN_PRODUCT_URL = "product_url";
    private static final String COLUMN_HIT_INTERVAL = "hit_interval";
    private static final String COLUMN_HIT_INTERVAL_TYPE = "hit_interval_type";
    private static Products instance;

    Products(Context context) {
        super(context, "products");
    }

    public static Products getInstance(Context context) {
        if (instance == null) {
            instance = new Products(context);
        }

        return instance;
    }

    public boolean exist(Product product) {
        return get(Products.COLUMN_SPECIAL_ID, product.getSpecialId(), Products.COLUMN_SOURCE, product.getSource(), Products.COLUMN_ID) != null;
    }


    @Override
    public long add(Product product) throws CartWatcherSQLException {

        final ContentValues cv = new ContentValues();
        cv.put(COLUMN_SPECIAL_ID, product.getSpecialId());
        cv.put(COLUMN_TITLE, product.getTitle());
        cv.put(COLUMN_SOURCE, product.getSource());
        cv.put(COLUMN_PRODUCT_URL, product.getProductUrl());
        cv.put(COLUMN_HIT_INTERVAL, product.getHitInterval());
        cv.put(COLUMN_HIT_INTERVAL_TYPE, product.getHitIntervalType());

        final long rowId = this.getWritableDatabase().insert(getTableName(), null, cv);
        if (rowId == -1) {
            throw new CartWatcherSQLException("Failed to add product");
        }
        return rowId;
    }
}
