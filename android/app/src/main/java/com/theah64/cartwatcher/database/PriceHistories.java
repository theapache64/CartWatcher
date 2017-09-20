package com.theah64.cartwatcher.database;

import android.content.Context;

import com.theah64.cartwatcher.models.PriceHistory;
import com.theah64.retrokit.database.AddBuilder;
import com.theah64.retrokit.database.BaseTable;

/**
 * Created by theapache64 on 16/9/17.
 */

public class PriceHistories extends BaseTable<PriceHistory> {

    private static final String COLUMN_PRODUCT_ID = "product_id";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_UPDATED_AT_IN_MILLIS = "updated_at_in_millis";
    private static PriceHistories instance;

    protected PriceHistories(Context context) {
        super(context, "price_histories");
    }

    public static PriceHistories getInstance(final Context context) {
        if (instance == null) {
            instance = new PriceHistories(context);
        }
        return instance;
    }

    @Override
    public long add(PriceHistory priceHistory) {
        return new AddBuilder(this)
                .add(COLUMN_PRODUCT_ID, priceHistory.getProductId())
                .add(COLUMN_PRICE, priceHistory.getPrice())
                .add(COLUMN_UPDATED_AT_IN_MILLIS, System.currentTimeMillis())
                .done(true);
    }

    public void addPriceIfChanged(PriceHistory priceHistory) {
        final long oldPrice = Long.parseLong(get(PriceHistories.COLUMN_PRODUCT_ID, priceHistory.getProductId(), PriceHistories.COLUMN_PRICE));
        if (oldPrice != priceHistory.getPrice()) {

            System.out.println("New price found : " + priceHistory);

            //Price updated
            add(priceHistory);

        } else {

            System.out.println("Found old price: " + priceHistory);

            //Price not updated, just update the hit time
            updateLastRow(PriceHistories.COLUMN_PRODUCT_ID, priceHistory.getProductId(), PriceHistories.COLUMN_UPDATED_AT_IN_MILLIS, String.valueOf(System.currentTimeMillis()));
        }
    }


}
