package com.theah64.cartwatcher.database;

import android.annotation.SuppressLint;
import android.content.Context;

import com.theah64.cartwatcher.models.Product;
import com.theah64.retrokit.database.AddBuilder;
import com.theah64.retrokit.database.BaseTable;
import com.theah64.retrokit.database.CustomCursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by theapache64 on 14/9/17.
 */

public class Products extends BaseTable<Product> {

    public static final String COLUMN_SPECIAL_ID = "special_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_SOURCE = "source";
    public static final String COLUMN_PRODUCT_URL = "product_url";
    public static final String COLUMN_HIT_INTERVAL = "hit_interval";
    public static final String COLUMN_HIT_INTERVAL_TYPE = "hit_interval_type";
    private static final String COLUMN_HIT_INTERVAL_IN_MILLIS = "hit_interval_in_millis";
    public static final String COLUMN_IMAGE_URL = "image_url";
    public static final String COLUMN_IS_HIT_ACTIVE = "is_hit_active";
    public static final String COLUMN_AS_CURRENT_PRICE = "current_price";
    public static final String COLUMN_AS_RECENT_PRICE = "recent_price";
    public static final String COLUMN_AS_LAST_HIT_IN_MILLIS = "last_hit_in_millis";
    public static final String COLUMN_NEXT_HIT_IN_MILLIS = "next_hit_in_millis";

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
                .add(COLUMN_NEXT_HIT_IN_MILLIS, product.getNextHitInMillis())
                .done(true);
    }

    @Override
    public List<Product> getAll() {
        final List<Product> products = new ArrayList<>();
        final CustomCursor cursor = new CustomCursor(this.getReadableDatabase().rawQuery("SELECT p.id, p.special_id, p.title, p.source, p.product_url, p.image_url, p.hit_interval, p.hit_interval_type, p.hit_interval_in_millis, p.is_hit_active, ph.price AS current_price, (SELECT price FROM price_histories WHERE product_id = ph.product_id ORDER BY id DESC LIMIT 1,2) AS recent_price, ph.updated_at_in_millis AS last_hit_in_millis, p.next_hit_in_millis FROM products p INNER JOIN price_histories ph ON ph.product_id = p.id GROUP BY p.id;", null));
        if (cursor.getCursor().moveToFirst()) {
            do {
                products.add(Product.parse(cursor));
            } while (cursor.getCursor().moveToNext());
        }

        cursor.getCursor().close();
        return products;
    }


}
