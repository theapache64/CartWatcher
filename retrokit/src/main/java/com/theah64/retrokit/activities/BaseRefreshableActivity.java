package com.theah64.retrokit.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.view.ViewParent;

import com.theah64.retrokit.R;
import com.theah64.retrokit.utils.ProgressManager;

/**
 * Created by theapache64 on 29/8/17.
 */

public abstract class BaseRefreshableActivity<DATA, APIINTERFACE> extends BaseAppCompatActivity {

    private ProgressManager progressMan;
    private boolean isSetupCalled;

    public abstract void loadData();

    public ProgressManager getProgressMan() {
        return progressMan;
    }

    public boolean isRefreshable() {
        return true;
    }


    public boolean isSetupCalled() {
        return isSetupCalled;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.isSetupCalled = false;
    }

    protected abstract int getMainViewID();

    public void setup() {

        this.isSetupCalled = true;

        if (isRefreshable()) {

            ViewParent view = findViewById(getMainViewID()).getParent();
            while (!(view instanceof SwipeRefreshLayout)) {
                view = view.getParent();
            }

            final SwipeRefreshLayout srl = (SwipeRefreshLayout) view;
            srl.setColorSchemeResources(R.color.primary, R.color.primary_dark);
            srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    srl.setRefreshing(false);
                    loadData();
                }

            });
        }

        progressMan = new ProgressManager(this, getMainViewID(), new ProgressManager.Callback() {
            @Override
            public void onRetryButtonClicked() {
                loadData();
            }
        });

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;

        if (getActionBarTitle() != null) {
            actionBar.setTitle(getActionBarTitle());
        }

        if (getActionBarSubTitle() != null) {
            actionBar.setSubtitle(getActionBarSubTitle());
        }

        loadData();

    }

    protected abstract String getActionBarSubTitle();

    protected abstract String getActionBarTitle();


}
