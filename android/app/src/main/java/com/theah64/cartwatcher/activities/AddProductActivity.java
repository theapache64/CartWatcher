package com.theah64.cartwatcher.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.theah64.cartwatcher.R;
import com.theah64.cartwatcher.widgets.ValidTextInputLayout;
import com.theah64.retrokit.activities.BaseAppCompatActivity;

import butterknife.BindView;

public class AddProductActivity extends BaseAppCompatActivity {

    @BindView(R.id.vtilProductURL)
    ValidTextInputLayout vtilProductURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewWithButterKnife(R.layout.activity_add_product);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        enableBackNavigation();

        final ValidTextInputLayout.InputValidator inputValidator = new ValidTextInputLayout.InputValidator(vtilProductURL);

        if (inputValidator.isAllValid()) {
            Toast.makeText(this, "Valid", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
        }

    }

}
