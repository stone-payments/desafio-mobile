package com.jademcosta.starstore.cart;


import android.content.Context;
import android.content.Intent;

import com.jademcosta.starstore.creditCard.CreditCardActivity;
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
        if(items.isEmpty()) {
            view.showEmptyState();
        } else {
            view.setCartItems(items);

            long total = 0;
            for(Item item : items) {
                total += item.getPrice();
            }

            view.setCartItemsTotalPrice(String.valueOf(total));
        }
    }

    @Override
    public void checkoutClicked(Context context) {
        if(model.getCartItemsList().isEmpty()) {
            return;
        }

        Intent intent = CreditCardActivity.newIntent(context);
        context.startActivity(intent);
    }
}
