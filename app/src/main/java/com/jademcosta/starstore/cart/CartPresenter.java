package com.jademcosta.starstore.cart;


import com.jademcosta.starstore.entity.Item;

import java.util.List;

public class CartPresenter implements CartContract.Model.Presenter, CartContract.View.Presenter {

    private CartContract.View view;
    private CartContract.Model model;

    public void setModel(CartContract.Model model) {
        this.model = model;
    }

    public void setView(CartContract.View view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        List<Item> items = model.getCartItemsList();
        view.setCartItems(items);

        long total = 0;
        for(Item item : items) {
            total += item.getPrice();
        }

        view.setCartItemsTotalPrice(String.valueOf(total));
    }
}
