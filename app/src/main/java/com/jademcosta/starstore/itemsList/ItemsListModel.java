package com.jademcosta.starstore.itemsList;


public class ItemsListModel implements ItemsListContract.Model {

    private Presenter presenter;

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}
