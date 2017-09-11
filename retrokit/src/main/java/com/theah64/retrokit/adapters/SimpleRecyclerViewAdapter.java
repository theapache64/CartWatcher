package com.theah64.retrokit.adapters;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.theah64.retrokit.R;
import com.theah64.retrokit.models.SimpleModel;

import java.util.List;

/**
 * Created by theapache64 on 24/8/17.
 */

public class SimpleRecyclerViewAdapter extends BaseRecyclerViewAdapter<SimpleRecyclerViewAdapter.ViewHolder, SimpleModel> {


    public SimpleRecyclerViewAdapter(List<SimpleModel> data, @Nullable Callback<SimpleModel> callback) {
        super(data, callback);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, SimpleModel item) {
        holder.tvTitle.setText(item.getTitle());
        holder.tvSubTitle.setText(item.getSubtitle());
    }

    @Override
    protected int getRowLayoutID() {
        return R.layout.simple_row;
    }

    @Override
    protected ViewHolder getNewRow(View row) {
        return new ViewHolder(row, this);
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        final TextView tvTitle, tvSubTitle;

        ViewHolder(View row, BaseRecyclerViewAdapter adapter) {
            super(row, adapter);
            this.tvTitle = (TextView) row.findViewById(R.id.tvTitle);
            this.tvSubTitle = (TextView) row.findViewById(R.id.tvSubTitle);
        }
    }
}
