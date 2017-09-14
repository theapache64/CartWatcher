package com.theah64.cartwatcher.server.core;

/**
 * Created by theapache64 on 13/9/17.3
 * <script type="a-state" data-a-state="{&quot;key&quot;:&quot;buyBackPageState&quot;}">
 */

public class Amazon extends BaseECommerceService {

    protected Amazon(String data) {
        super(data);
    }

    @Override
    public Product getProduct() throws CartWatcherException {
        final String[] f1Arr = getData().split("<span id=\"priceblock_");
        if (f1Arr.length > 1) {
            final String f1 = f1Arr[1];
            final String title = getData().split("<span id=\"productTitle\" class=\"a-size-large\">")[1].split("</span>")[0].trim();
            final long price = Double.valueOf(f1.split("</span>")[1].split("</span>")[0].replaceAll(",", "").trim()).longValue();
            return new Product(title, price, Product.SOURCE_AMAZON);
        } else {
            throw new CartWatcherException("Invalid amazon product url");
        }
    }
}
