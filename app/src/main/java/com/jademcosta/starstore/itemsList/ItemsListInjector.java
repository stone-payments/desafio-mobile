package com.jademcosta.starstore.itemsList;


import android.content.Context;

import com.jademcosta.starstore.database.ItemsRepository;
import com.jademcosta.starstore.network.ItemsApi;

public class ItemsListInjector {

    private Context context;

    public void inject(ItemsListActivity view, Context context) {
        this.context = context.getApplicationContext();

        ItemsListModel model = buildModel();
        ItemsListPresenter presenter = new ItemsListPresenter();

        model.setPresenter(presenter);
        view.setPresenter(presenter);
        presenter.setModel(model);
        presenter.setView(view);
    }

    private ItemsListModel buildModel() {
        return new ItemsListModel(new ItemsApi(), new ItemsRepository(context));
    }
}
