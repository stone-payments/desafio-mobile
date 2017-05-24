package com.stone.lfernandosantos.storewars.controlers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stone.lfernandosantos.storewars.R;
import com.stone.lfernandosantos.storewars.models.Product;

import java.util.List;

/**
 * Created by lf.fernandodossantos on 20/05/17.
 */

public class ListProductsAdapter extends RecyclerView.Adapter<ListProductsAdapter.MyViewHolder> {

    private List<Product> products;
    private LayoutInflater inflater;
    private Context context;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public ListProductsAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = inflater.inflate(R.layout.item_adapter_product, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Glide.with(context)
                .load(products.get(position).thumbnailHd)
                .into(holder.imgViewAdapter);

        holder.txtTitle.setText(products.get(position).title);
        holder.txtSeller.setText(products.get(position).seller);

        holder.txtPrice.setText("R$ "+ products.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public void setmRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack) {
        this.mRecyclerViewOnClickListenerHack = mRecyclerViewOnClickListenerHack;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public ImageView imgViewAdapter;
        public TextView txtTitle;
        public TextView txtPrice;
        public TextView txtSeller;

        public MyViewHolder(View itemView) {
            super(itemView);

            imgViewAdapter = (ImageView) itemView.findViewById(R.id.imgProductAdapter);
            txtTitle = (TextView) itemView.findViewById(R.id.txtViewTitle);
            txtSeller = (TextView) itemView.findViewById(R.id.txtViewSeller);
            txtPrice = (TextView) itemView.findViewById(R.id.txtViewPrice);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(v, getPosition());
            }

        }
    }
}
