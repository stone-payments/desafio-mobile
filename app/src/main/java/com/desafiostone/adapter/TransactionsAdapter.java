package com.desafiostone.adapter;

import android.content.Context;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.desafiostone.R;
import com.desafiostone.database.RealmDatabase;
import com.desafiostone.domain.Products;
import com.desafiostone.domain.Transaction;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by Filipi Andrade on 17-Oct-17.
 */

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ProductViewHolder> {

    private Context mContext;
    private RealmResults<Transaction> mTransactions;

    public TransactionsAdapter(Context c, RealmResults<Transaction> t) {
        this.mContext = c;
        this.mTransactions = t;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_transaction, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        holder.tvDateHour.setText(mTransactions.get(position).getDate_hour());
        holder.tvName.setText(mTransactions.get(position).getCard_holder_name());
        holder.tvValue.setText(String.valueOf(mTransactions.get(position).getValue()));
    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView tvDateHour, tvValue, tvName;

        public ProductViewHolder(View itemView) {
            super(itemView);

            tvDateHour = itemView.findViewById(R.id.tvDateHour);
            tvValue = itemView.findViewById(R.id.tvValue);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
