package com.germano.desafiostone.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.germano.desafiostone.R;
import com.germano.desafiostone.models.Product;
import com.germano.desafiostone.utils.FormatNumber;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;


/**
 * Created by germano on 24/08/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> mProducts;
    private Consumer<Product> onProductClicked;
    private Consumer<Integer> onDeleteClicked;
    private Context mContext;
    private String type;

    public ProductAdapter(Context context, List<Product> products, String type) {
        mProducts = products;
        mContext = context;
        this.type = type;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textview_price_product)
        TextView mTextViewPriceProduct;

        @BindView(R.id.textview_title_product)
        TextView mTextViewTitleProduct;

        @BindView(R.id.imageview_product)
        ImageView mImageViewProduct;

        @BindView(R.id.textview_seller_product)
        TextView mTextViewSeller;

        @BindView(R.id.cardview_product)
        CardView mCardViewProduct;

        @BindView(R.id.imageview_delete)
        ImageView mImageViewDelete;

        Product item;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(final Product item) {
            this.item = item;
            mTextViewTitleProduct.setText(item.getTitle());
            mTextViewPriceProduct.setText(mContext.getString(
                    R.string.total_value, FormatNumber.set(item.getPrice())));
            mTextViewSeller.setText(item.getSeller());

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.placeholder);
            requestOptions.error(R.drawable.placeholder);

            Glide.with(mContext)
                    .setDefaultRequestOptions(requestOptions)
                    .load(item.getUrlImage())
                    .into(mImageViewProduct);

            animateFade(mCardViewProduct);

            mCardViewProduct.setOnClickListener(view -> {
                try {
                    onProductClicked.accept(item);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            if("cart".equalsIgnoreCase(type)){
                mImageViewDelete.setVisibility(View.VISIBLE);

                mImageViewDelete.setOnClickListener(v -> {
                    try {
                        onDeleteClicked.accept(getAdapterPosition());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mProducts.get(position));
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }


    private void animateFade(CardView cardView){
        cardView.setAlpha(0.3f);
        cardView.animate()
                .alpha(1)
                .setDuration(800)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    public void setOnProductClicked(Consumer<Product> onProductClicked) {
        this.onProductClicked = onProductClicked;
    }

    public void setOnDeleteClicked(Consumer onDeleteClicked){
        this.onDeleteClicked = onDeleteClicked;
    }
}
