package com.jademcosta.starstore.itemsList;


import com.jademcosta.starstore.entity.Item;

import java.util.List;

public class ItemsListPresenter implements ItemsListContract.Model.Presenter,
        ItemsListContract.View.Presenter {

    private ItemsListContract.View view;
    private ItemsListContract.Model model;

    @Override
    public void onCreate() {
        view.showLoading();
        view.hideList();
        model.getItemsList();
    }

    @Override
    public void setView(ItemsListContract.View view) {
        this.view = view;
    }

    @Override
    public void itemClicked(Item item) {
        model.addItemToCart(item);
    }

    @Override
    public void setModel(ItemsListContract.Model model) {
        this.model = model;
    }

    @Override
    public void onItemsListFetchFailure() {
        //TODO: jade: show error view
    }

    @Override
    public void onItemsListFetched(List<Item> items) {
        view.setListItems(items);
        view.showList();
        view.hideLoading();
    }
}
