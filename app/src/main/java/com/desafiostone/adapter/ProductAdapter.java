package com.desafiostone.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import com.desafiostone.domain.Products;

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
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.tvTitle.setText(mProducts.get(position).getTitle());

        holder.tvPrice.setText(mProducts.get(position).getPrice());

        String seller = mContext.getResources().getString(R.string.seller) + mProducts.get(position).getSeller();
        holder.tvSeller.setText(Html.fromHtml(seller));

        new DownloadImageAsyncTask(mContext, holder.ivThumb, mProducts.get(position).getThumbnailHd()).execute();
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
            Toast.makeText(mContext, "Adicionado", Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadImageAsyncTask extends AsyncTask<Void, Void, Void> {

        Context context;
        ImageView imageView;
        String string;
        Bitmap bitmap;

        public DownloadImageAsyncTask(Context c, ImageView iv, String s) {
            this.context = c;
            this.imageView = iv;
            this.string = s;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(string);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            imageView.setImageBitmap(bitmap);
            super.onPostExecute(aVoid);
        }
    }
}
