package com.theah64.cartwatcher.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.theah64.cartwatcher.R;
import com.theah64.cartwatcher.adapters.ProductsAdapter;
import com.theah64.cartwatcher.database.Products;
import com.theah64.cartwatcher.models.Product;
import com.theah64.retrokit.activities.BaseAppCompatActivity;
import com.theah64.retrokit.utils.ProgressManager;
import com.theah64.retrokit.widgets.CustomRecyclerView;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseAppCompatActivity implements ProductsAdapter.ProductsAdapterCallback {

    @BindView(R.id.crvProducts)
    CustomRecyclerView crvProducts;

    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewWithButterKnife(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddProductActivity.class));
            }
        });

        //Getting all products from database
        products = Products.getInstance(this).getAll();

        crvProducts.setLayoutManager(new LinearLayoutManager(this));
        crvProducts.setAdapter(new ProductsAdapter(this, products, this), R.string.No_product_added, new ProgressManager.Callback() {
            @Override
            public void onRetryButtonClicked() {
                startActivity(new Intent(MainActivity.this, AddProductActivity.class));
            }
        }, getString(R.string.ADD));

    }

    private static final String X = MainActivity.class.getSimpleName();


    @Override
    public void onItemClick(Product product, int position) {

    }

    @Override
    public void onHitControllerClicked(int position) {

    }
}
