package com.desafiostone.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.desafiostone.R;
import com.desafiostone.database.RealmDatabase;
import com.desafiostone.domain.Products;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Filipi Andrade on 17-Oct-17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context mContext;
    private ArrayList<Products> mProducts;

    private String TAG = "TAG";

    public ProductAdapter(Context c, ArrayList<Products> p) {
        this.mContext = c;
        this.mProducts = p;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_product, parent, false);
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

        ImageView ivThumb, ivAddCart;
        TextView tvTitle, tvPrice, tvSeller;

        public ProductViewHolder(View itemView) {
            super(itemView);

            ivThumb = itemView.findViewById(R.id.ivThumb);
            ivAddCart = itemView.findViewById(R.id.ivAddCart);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvSeller = itemView.findViewById(R.id.tvSeller);

            ivAddCart.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                RealmDatabase.getInstance().setContext(mContext);
                RealmDatabase.getInstance().addCart(mProducts.get(getAdapterPosition()));
                Toast.makeText(mContext, mProducts.get(getAdapterPosition()).getTitle() + " foi adicionado no carrinho!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
