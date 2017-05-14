package com.jademcosta.starstore.itemsList;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jademcosta.starstore.R;
import com.jademcosta.starstore.entity.Item;

import java.util.List;

public class ItemsListAdapter extends RecyclerView.Adapter<ItemsListAdapter.ItemsViewHolder> {

    private List<Item> items;
    private OnItemClickListener listener;

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
        holder.price.setText(String.valueOf(item.getPrice()));
        holder.sellerName.setText(item.getSeller());

        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.onItemClicked(item);
                }
            }
        });

        Glide.with(holder.itemView.getContext()).load(item.getThumbnailHd()).asBitmap()
                .placeholder(R.drawable.item_placeholder)
                .fallback(R.drawable.item_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView sellerName;
        TextView price;
        ImageView image;
        View addToCartButton;

        public ItemsViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.items_list_item_name);
            sellerName = (TextView) itemView.findViewById(R.id.items_list_item_seller_name);
            price = (TextView) itemView.findViewById(R.id.items_list_item_price);
            image = (ImageView) itemView.findViewById(R.id.items_list_item_image);
            addToCartButton = itemView.findViewById(R.id.items_list_item_add_to_cart_button);
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(Item item);
    }
}
