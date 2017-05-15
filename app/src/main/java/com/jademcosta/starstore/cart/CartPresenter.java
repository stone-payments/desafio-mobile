package com.jademcosta.starstore.cart;


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
        view.setCartItems(model.getCartItemsList());
    }
}
