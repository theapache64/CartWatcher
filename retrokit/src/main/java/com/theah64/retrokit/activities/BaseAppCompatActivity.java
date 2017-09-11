package com.theah64.retrokit.activities;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by theapache64 on 24/8/17.
 */

public abstract class BaseAppCompatActivity extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void setContentViewWithButterKnife(int layoutId) {
        setContentView(layoutId);
        ButterKnife.bind(this);
    }

    public void enableBackNavigation() {
        configActionbar(null, null, true);
    }

    protected void enableBackNavigationWithTitle(final String title) {
        configActionbar(title, null, true);
    }

    protected void configActionbar(@Nullable final String title, @Nullable final String subtitle, boolean isBackNav) {
        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;

        if (isBackNav) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (title != null) {
            actionBar.setTitle(title);
        }

        if (subtitle != null) {
            actionBar.setSubtitle(subtitle);
        }


    }

    //Enabling back navigation
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //Close this activity
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    public String getLogTag() {
        return this.getClass().getSimpleName();
    }
}
