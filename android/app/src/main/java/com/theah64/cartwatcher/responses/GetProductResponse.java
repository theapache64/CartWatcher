package com.theah64.cartwatcher.responses;

import com.google.gson.annotations.SerializedName;
import com.theah64.cartwatcher.models.Product;

/**
 * Created by theapache64 on 14/9/17.
 */

public class GetProductResponse {

    @SerializedName("product")
    private final Product product;

    public GetProductResponse(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}
