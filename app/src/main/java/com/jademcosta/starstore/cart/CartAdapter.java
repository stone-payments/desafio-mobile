package com.jademcosta.starstore.cart;


import android.view.View;
import android.view.ViewGroup;

import com.jademcosta.starstore.entity.Item;
import com.jademcosta.starstore.itemsList.ItemsListAdapter;

import java.util.List;

public class CartAdapter extends ItemsListAdapter {

    public CartAdapter(List<Item> items) {
        super(items);
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ItemsViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.addToCartButton.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}
