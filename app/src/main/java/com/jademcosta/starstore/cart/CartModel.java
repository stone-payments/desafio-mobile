package com.jademcosta.starstore.cart;


import com.jademcosta.starstore.database.ItemsRepository;

public class CartModel implements CartContract.Model {

    private Presenter presenter;
    private ItemsRepository repository;

    public CartModel(ItemsRepository repository) {
        this.repository = repository;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}
