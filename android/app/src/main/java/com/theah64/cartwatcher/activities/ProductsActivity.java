package com.theah64.cartwatcher.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.theah64.cartwatcher.R;
import com.theah64.cartwatcher.adapters.ProductsAdapter;
import com.theah64.cartwatcher.database.Products;
import com.theah64.cartwatcher.models.Product;
import com.theah64.cartwatcher.utils.CartWatcher;
import com.theah64.cartwatcher.utils.PriceUpdaterCallback;
import com.theah64.retrokit.activities.BaseAppCompatActivity;
import com.theah64.retrokit.utils.ProgressManager;
import com.theah64.retrokit.widgets.CustomRecyclerView;

import java.util.List;

import butterknife.BindView;

public class ProductsActivity extends BaseAppCompatActivity implements ProductsAdapter.ProductsAdapterCallback, PriceUpdaterCallback {

    @BindView(R.id.crvProducts)
    CustomRecyclerView crvProducts;

    private List<Product> products;
    private ProductsAdapter productsAdapter;
    private Products productsTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewWithButterKnife(R.layout.activity_products);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAddProductActivity();
            }
        });

        //Getting all products from database
        productsTable = Products.getInstance(this);
        products = productsTable.getAll();
        productsAdapter = new ProductsAdapter(this, products, this);

        crvProducts.setLayoutManager(new LinearLayoutManager(this));
        crvProducts.setAdapter(productsAdapter, R.string.No_product_added, new ProgressManager.Callback() {
            @Override
            public void onRetryButtonClicked() {
                launchAddProductActivity();
            }
        }, getString(R.string.ADD));


    }

    private void launchAddProductActivity() {
        startActivityForResult(new Intent(ProductsActivity.this, AddProductActivity.class), AddProductActivity.RQ_CODE);
    }

    private static final String X = ProductsActivity.class.getSimpleName();


    @Override
    public void onItemClick(Product product, int position) {

    }

    @Override
    public void onHitControllerClicked(int position) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AddProductActivity.RQ_CODE && resultCode == AddProductActivity.RESULT_OK) {
            //New product added
            products.add((Product) data.getSerializableExtra(Product.KEY));
            productsAdapter.notifyItemInserted(0);

            if (products.size() == 1) {
                //it's first product, so we got to hide the previous error
                crvProducts.hideError();
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        CartWatcher.setCallback(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        CartWatcher.setCallback(null);
    }


    @Override
    public void onProductUpdated(String productId) {
        System.out.println("Product updated in UI: " + productId);
        final Product product = productsTable.get(Products.COLUMN_ID, productId);

        System.out.println("Product is :" + productId);

        //Replacing list item
        final int replacePosition = new ListItemReplacer<Product>(products) {
            @Override
            String getReplaceProperty(Product product) {
                return product.getId();
            }
        }.replace(productId).with(product);


        if (replacePosition != -1) {
            System.out.println("Product position updated");
            productsAdapter.notifyItemChanged(replacePosition);
        }

    }

    abstract class ListItemReplacer<T> {
        private final List<T> list;
        private String findProperty;

        public ListItemReplacer(List<T> list) {
            this.list = list;
        }

        abstract String getReplaceProperty(T t);

        public ListItemReplacer<T> replace(String findProperty) {
            this.findProperty = findProperty;
            return this;
        }

        public int with(T t) {
            //First getting the index
            int pos = -1;
            for (int i = 0; i < list.size(); i++) {
                final T t1 = list.get(i);
                if (getReplaceProperty(t1).equals(findProperty)) {
                    pos = i;
                    break;
                }
            }

            if (pos != -1) {
                list.remove(pos);
                list.add(pos, t);
                Log.i(X, "Match found");
            } else {
                Log.e(X, "No match found");
            }

            return pos;
        }
    }
}
