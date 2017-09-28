package com.theah64.retrokit.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.theah64.retrokit.utils.ProgressManager;

/**
 * Created by theapache64 on 16/9/17.
 */

public class CustomRecyclerView extends RecyclerView {


    private ProgressManager pm;
    private int errorIfEmptyList;

    public CustomRecyclerView(Context context) {
        super(context);
    }


    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setAdapter(Adapter productsAdapter, int errorIfEmptyList, ProgressManager.Callback callback, String retryButtonText) {
        setAdapter(productsAdapter);
        pm = new ProgressManager(getContext(), (ViewGroup) getParent(), getId(), callback, retryButtonText);
        this.errorIfEmptyList = errorIfEmptyList;
        updateErrorMessage();
    }

    private void updateErrorMessage() {
        if (getAdapter().getItemCount() == 0) {
            //empty list. hide the recyclerview and show the custom error
            pm.showError(ProgressManager.ERROR_TYPE_EMPTY, errorIfEmptyList);
        } else {
            pm.showMainView();
        }
    }

    public void hideError() {
        pm.showMainView();
    }
}
