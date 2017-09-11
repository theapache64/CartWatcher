package com.theah64.retrokit.activities;

import android.support.v7.widget.RecyclerView;

import com.theah64.retrokit.adapters.BaseRecyclerViewAdapter;
import com.theah64.retrokit.retro.BaseAPIResponse;

import java.util.List;

/**
 * Created by theapache64 on 24/8/17.
 */

public abstract class BaseRecyclerViewActivity<MODEL, RESPONSE_DATA, APIINTERFACE> extends BaseDynamicActivity<RESPONSE_DATA, APIINTERFACE> {

    protected abstract RecyclerView getRecyclerView();

    @Override
    protected void onSuccess(BaseAPIResponse<RESPONSE_DATA> body) {
        getRecyclerView().setAdapter(getNewAdapter(getData(body.getData())));
    }

    public abstract List<MODEL> getData(RESPONSE_DATA data);

    public abstract BaseRecyclerViewAdapter getNewAdapter(List<MODEL> data);

}
