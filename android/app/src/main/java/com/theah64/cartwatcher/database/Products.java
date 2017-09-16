package com.theah64.cartwatcher.database;

import android.annotation.SuppressLint;
import android.content.Context;

import com.theah64.cartwatcher.models.Product;
import com.theah64.retrokit.database.AddBuilder;
import com.theah64.retrokit.database.BaseTable;

import java.util.ArrayList;
import java.util.List;

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
    private static final String COLUMN_HIT_INTERVAL_IN_MILLIS = "hit_interval_in_millis";
    private static final String COLUMN_IMAGE_URL = "image_url";
    private static final String COLUMN_IS_HIT_ACTIVE = "is_hit_active";

    @SuppressLint("StaticFieldLeak")
    private static Products instance;

    private Products(Context context) {
        super(context, "products");
    }

    public static Products getInstance(Context context) {
        if (instance == null) {
            instance = new Products(context);
        } else {
            instance.setContext(context);
        }

        return instance;
    }

    public boolean exist(Product product) {
        return get(Products.COLUMN_SPECIAL_ID, product.getSpecialId(), Products.COLUMN_SOURCE, product.getSource(), Products.COLUMN_ID) != null;
    }


    @Override
    public long add(Product product) {

        return new AddBuilder(this)
                .add(COLUMN_SPECIAL_ID, product.getSpecialId())
                .add(COLUMN_TITLE, product.getTitle())
                .add(COLUMN_SOURCE, product.getSource())
                .add(COLUMN_PRODUCT_URL, product.getProductUrl())
                .add(COLUMN_HIT_INTERVAL, product.getHitInterval())
                .add(COLUMN_HIT_INTERVAL_TYPE, product.getHitIntervalType())
                .add(COLUMN_HIT_INTERVAL_IN_MILLIS, product.getHitIntervalInMillis())
                .add(COLUMN_IMAGE_URL, product.getImageUrl())
                .add(COLUMN_IS_HIT_ACTIVE, product.isHitActive())
                .done(true);
    }

    @Override
    public List<Product> getAll() {
        return new ArrayList<>();
    }


}
