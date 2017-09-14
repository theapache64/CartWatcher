package com.theah64.cartwatcher.database;

import android.content.ContentValues;
import android.content.Context;

import com.theah64.cartwatcher.models.Product;

/**
 * Created by theapache64 on 14/9/17.
 */

public class Products extends BaseTable<Product> {

    private static final String COLUMN_SPECIAL_ID = "special_id";
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
        return get(Products.COLUMN_SPECIAL_ID, product.getSpecialId(), Products.COLUMN_ID) != null;
    }

    @Override
    public long add(Product product) {

        final ContentValues cv = new ContentValues();
        cv.put(COLUMN_SPECIAL_ID, );

        return super.add(newInstance);
    }
}
