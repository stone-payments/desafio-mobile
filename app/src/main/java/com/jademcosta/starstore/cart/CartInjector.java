package com.jademcosta.starstore.cart;


import android.content.Context;

import com.jademcosta.starstore.database.ItemsRepository;

public class CartInjector {

    private Context context;

    public void inject(CartActivity view, Context context) {
        this.context = context.getApplicationContext();

        CartModel model = buildModel();
        CartPresenter presenter = new CartPresenter();

        model.setPresenter(presenter);
        view.setPresenter(presenter);
        presenter.setModel(model);
        presenter.setView(view);
    }

    private CartModel buildModel() {
        return new CartModel(new ItemsRepository(context));
    }

}
