package com.theah64.cartwatcher.adapters;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconButton;
import com.theah64.cartwatcher.R;
import com.theah64.cartwatcher.models.Product;
import com.theah64.retrokit.adapters.BaseRecyclerViewAdapter;
import com.theah64.retrokit.adapters.BaseRecyclerViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by theapache64 on 15/9/17.
 */

public class ProductsAdapter extends BaseRecyclerViewAdapter<ProductsAdapter.ViewHolder, Product> {

    public ProductsAdapter(List<Product> data, @Nullable Callback<Product> callback) {
        super(data, callback);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, Product item) {

    }

    @Override
    protected int getRowLayoutID() {
        return R.layout.products_row;
    }

    @Override
    protected ViewHolder getNewRow(View row) {
        return new ViewHolder(row, this);
    }

    class ViewHolder extends BaseRecyclerViewHolder<Product> {

        @BindView(R.id.ivProductImage)
        ImageView ivProductImage;

        @BindView(R.id.tvProductTitle)
        TextView tvProductTitle;

        @BindView(R.id.tvCurrentProductPrice)
        TextView tvCurrentProductPrice;

        @BindView(R.id.tvPriceFluctuated)
        TextView tvPriceFluctuated;

        @BindView(R.id.pbNextHit)
        ProgressBar pbNextHit;

        @BindView(R.id.ibHitControl)
        IconButton ibHitControl;

        public ViewHolder(View row, BaseRecyclerViewAdapter adapter) {
            super(row, adapter);
            ButterKnife.bind(this, row);
        }
    }

}
