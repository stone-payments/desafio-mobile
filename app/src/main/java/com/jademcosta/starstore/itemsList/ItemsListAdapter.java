package com.jademcosta.starstore.itemsList;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jademcosta.starstore.R;
import com.jademcosta.starstore.entity.Item;

import java.util.List;

public class ItemsListAdapter extends RecyclerView.Adapter<ItemsListAdapter.ItemsViewHolder> {

    private List<Item> items;

    public ItemsListAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_list_row,
                parent, false);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemsViewHolder holder, int position) {
        final Item item = items.get(position);

        holder.name.setText(item.getTitle());
        holder.price.setText(item.getPrice());
        holder.sellerName.setText(item.getSeller());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView sellerName;
        TextView price;
        ImageView image;

        public ItemsViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.items_list_item_name);
            sellerName = (TextView) itemView.findViewById(R.id.items_list_item_seller_name);
            price = (TextView) itemView.findViewById(R.id.items_list_item_price);
            image = (ImageView) itemView.findViewById(R.id.items_list_item_image);
        }
    }
}
