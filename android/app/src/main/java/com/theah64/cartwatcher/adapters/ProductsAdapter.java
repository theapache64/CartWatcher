package com.theah64.cartwatcher.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.theah64.cartwatcher.R;
import com.theah64.cartwatcher.models.Product;
import com.theah64.retrokit.adapters.BaseButterknifeRecyclerViewHolder;
import com.theah64.retrokit.adapters.BaseRecyclerViewAdapter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by theapache64 on 15/9/17.
 */

public class ProductsAdapter extends BaseRecyclerViewAdapter<ProductsAdapter.ViewHolder, Product> {

    private final Context context;
    private final ProductsAdapterCallback callback;

    public ProductsAdapter(Context context, List<Product> data, @Nullable ProductsAdapterCallback callback) {
        super(data, callback);
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position, final Product product) {

        ImageLoader.getInstance().displayImage(product.getImageUrl(), holder.ivProductImage);

        holder.tvProductTitle.setText(product.getTitle());
        holder.tvCurrentProductPrice.setText(String.valueOf(product.getCurrentPrice()));


        if (product.getPriceFluctuated() != 0) {

            holder.ctvPriceFluctuated.setVisibility(View.VISIBLE);
            holder.itvPriceFluctuationIndicator.setVisibility(View.VISIBLE);

            holder.ctvPriceFluctuated.setText(String.valueOf(Math.abs(product.getPriceFluctuated())));

            final boolean isPriceRise = product.getPriceFluctuated() > 0;
            holder.itvPriceFluctuationIndicator.setText(isPriceRise ? R.string.fa_caret_up_iconify : R.string.fa_caret_down_iconify);
            holder.itvPriceFluctuationIndicator.setTextColor(ContextCompat.getColor(context, isPriceRise ? R.color.red_500 : R.color.green_500));
        } else {
            holder.ctvPriceFluctuated.setVisibility(View.GONE);
            holder.itvPriceFluctuationIndicator.setVisibility(View.GONE);
        }

        //Starting count down timer
        holder.getTimer().cancel();
        holder.setTimer(new Timer());
        holder.getTimer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                holder.pbNextHit.setProgress(product.getNextHitProgress());
            }
        }, 0, 100);

        holder.ibHitControl.setText(product.isHitActive() ? R.string.fa_stop_circle_o_iconify : R.string.fa_play_circle_o_iconify);
    }


    @Override
    protected int getRowLayoutID() {
        return R.layout.products_row;
    }


    @Override
    protected ViewHolder getNewRow(View row) {
        return new ViewHolder(row, this);
    }

    class ViewHolder extends BaseButterknifeRecyclerViewHolder<Product> {

        @BindView(R.id.ivProductImage)
        ImageView ivProductImage;

        @BindView(R.id.tvProductTitle)
        TextView tvProductTitle;

        @BindView(R.id.tvCurrentProductPrice)
        TextView tvCurrentProductPrice;

        @BindView(R.id.ctvPriceFluctuated)
        TextView ctvPriceFluctuated;

        @BindView(R.id.pbNextHit)
        ProgressBar pbNextHit;

        @BindView(R.id.ibHitControl)
        IconTextView ibHitControl;

        @BindView(R.id.itvPriceFluctuationIndicator)
        IconTextView itvPriceFluctuationIndicator;


        Timer timer = new Timer();

        ViewHolder(View row, BaseRecyclerViewAdapter adapter) {
            super(row, adapter);
        }

        @OnClick(R.id.ibHitControl)
        void onHitControlClicked() {
            callback.onHitControllerClicked(getLayoutPosition());
        }

        public Timer getTimer() {
            return timer;
        }

        public void setTimer(Timer timer) {
            this.timer = timer;
        }
    }

    public interface ProductsAdapterCallback extends Callback<Product> {
        void onHitControllerClicked(int position);
    }

}
