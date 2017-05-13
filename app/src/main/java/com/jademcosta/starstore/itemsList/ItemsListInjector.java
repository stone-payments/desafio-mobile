package com.jademcosta.starstore.itemsList;


public class ItemsListInjector {

    public void inject(ItemsListContract.View view) {
        ItemsListModel model = new ItemsListModel();
        ItemsListPresenter presenter = new ItemsListPresenter();
        model.setPresenter(presenter);
        view.setPresenter(presenter);
    }
}
