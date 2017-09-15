package com.theah64.cartwatcher.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.theah64.cartwatcher.R;
import com.theah64.retrokit.activities.BaseAppCompatActivity;

public class AddProductActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

}
