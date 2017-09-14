package com.theah64.cartwatcher.server.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by theapache64 on 13/9/17.
 */

public class Flipkart extends BaseECommerceService {

    protected Flipkart(String data) {
        super(data);
    }

    @Override
    public Product getProduct() throws CartWatcherException {


        final String[] f1Arr = getData().split("window.__INITIAL_STATE__ = ");
        final String jsonString = f1Arr[1].split(";\n</script>")[0].trim();

        try {
            final JSONObject joProduct = new JSONObject(jsonString);
            final JSONObject joProductDetails = joProduct
                    .getJSONObject("productPage")
                    .getJSONObject("productDetails");

            if (joProductDetails.has("data")) {

                final JSONObject joProductData = joProductDetails.getJSONObject("data");

                //product_breadcrumb
                final JSONArray jaProductBreadcrumbs =
                        joProductData.getJSONObject("product_breadcrumb").getJSONArray("data").getJSONObject(0)
                                .getJSONObject("value")
                                .getJSONArray("productBreadcrumbs");

                final String title = jaProductBreadcrumbs.getJSONObject(jaProductBreadcrumbs.length() - 1).getString("title");

                final long price =
                        joProductData.getJSONObject("product_seller_detail_1")
                                .getJSONArray("data")
                                .getJSONObject(0)
                                .getJSONObject("value")
                                .getJSONObject("metadata")
                                .getLong("price");

                return new Product(title, price, Product.SOURCE_FLIPKART);
            } else {
                throw new CartWatcherException("Invalid flipkart product URL:1");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new CartWatcherException("Invalid flipkart product URL:2");
        }
    }
}
