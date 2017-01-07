package com.luiztorres.stone.starwarsshop.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luiztorres.stone.starwarsshop.R;
import com.luiztorres.stone.starwarsshop.extras.Order;

import java.util.List;

/**
 * Created by Dindal on 31/12/2016.
 */

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.MyViewHolder>{
    private Context ctx;
    private List<Order> itemList;

    public OrdersListAdapter(Context c, List<Order> list)
    {
        this.ctx = c;
        this.itemList = list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, value, date, card;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_order_layout_name);
            value = (TextView) view.findViewById(R.id.tv_order_layout_value);
            date = (TextView) view.findViewById(R.id.tv_order_layout_date);
            card = (TextView) view.findViewById(R.id.tv_order_layout_card_number);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_order_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Order i = itemList.get(position);
        holder.name.setText(i.getName());
        holder.value.setText("R$:"+i.getValue());
        holder.date.setText(i.getDate());
        holder.card.setText(i.getLastNubmers());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
