package com.desafiostone.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.desafiostone.R;
import com.desafiostone.database.RealmDatabase;
import com.desafiostone.domain.Products;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by Filipi Andrade on 17-Oct-17.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ProductViewHolder> {

    private Context mContext;
    private RealmResults<Products> mProducts;

    private String TAG = "TAG";

    public CartAdapter(Context c, RealmResults<Products> p) {
        this.mContext = c;
        this.mProducts = p;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_cart, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        holder.tvTitle.setText(mProducts.get(position).getTitle());

        holder.tvPrice.setText(mProducts.get(position).getPrice());

        String seller = mContext.getResources().getString(R.string.seller) + mProducts.get(position).getSeller();
        holder.tvSeller.setText(Html.fromHtml(seller));

        Picasso.with(mContext).load(mProducts.get(position).getThumbnailHd()).into(holder.ivThumb);
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivThumb, ivRemove;
        TextView tvTitle, tvPrice, tvSeller;

        public ProductViewHolder(View itemView) {
            super(itemView);

            ivThumb = itemView.findViewById(R.id.ivThumb);
            ivRemove = itemView.findViewById(R.id.ivRemove);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvSeller = itemView.findViewById(R.id.tvSeller);

            ivRemove.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final String title = mProducts.get(getAdapterPosition()).getTitle();

            new MaterialDialog.Builder(mContext)
                    .title(mContext.getResources().getString(R.string.warning))
                    .content(mContext.getResources().getString(R.string.delete_item))
                    .negativeText(mContext.getResources().getString(R.string.no))
                    .positiveText(mContext.getResources().getString(R.string.yes))
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            RealmDatabase.getInstance().setContext(mContext);
                            RealmDatabase.getInstance().removeFromCart(getAdapterPosition());
                            notifyDataSetChanged();
                            notifyItemChanged(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            Toast.makeText(mContext, title + " foi removido do carrinho!", Toast.LENGTH_SHORT).show();

                            if (RealmDatabase.getInstance().isEmptyCart()) {
                                ((Activity) mContext).finish();
                            }
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    })
                    .show();

        }
    }
}
