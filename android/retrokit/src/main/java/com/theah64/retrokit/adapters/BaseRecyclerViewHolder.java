package com.theah64.retrokit.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;


/**
 * Created by theapache64 on 9/2/17.
 */

public class BaseRecyclerViewHolder<M> extends RecyclerView.ViewHolder {

    public BaseRecyclerViewHolder(View row, final BaseRecyclerViewAdapter adapter) {
        super(row);
        ButterKnife.bind(this, row);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getCallback() != null) {
                    adapter.getCallback().onItemClick(adapter.getData().get(getLayoutPosition()), getLayoutPosition());
                }
            }
        });
    }
}
