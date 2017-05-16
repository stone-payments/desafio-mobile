package com.jademcosta.starstore.transactionsList;


import android.content.Context;

import com.jademcosta.starstore.database.TransactionsRepository;

public class TransactionsListInjector {

    private Context context;

    public void inject(TransactionsListActivity view, Context context) {
        this.context = context;

        TransactionsListModel model = buildModel();
        TransactionsListPresenter presenter = new TransactionsListPresenter();

        model.setPresenter(presenter);
        view.setPresenter(presenter);
        presenter.setModel(model);
        presenter.setView(view);
    }

    private TransactionsListModel buildModel() {
        return new TransactionsListModel(new TransactionsRepository(context));
    }
}
