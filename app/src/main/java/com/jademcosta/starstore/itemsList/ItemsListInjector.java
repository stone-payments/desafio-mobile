package com.jademcosta.starstore.itemsList;


import com.jademcosta.starstore.network.ItemsApi;

public class ItemsListInjector {

    public void inject(ItemsListActivity view) {
        ItemsListModel model = buildModel();
        ItemsListPresenter presenter = new ItemsListPresenter();

        model.setPresenter(presenter);
        view.setPresenter(presenter);
        presenter.setModel(model);
        presenter.setView(view);
    }

    private ItemsListModel buildModel() {
        return new ItemsListModel(new ItemsApi());
    }
}
