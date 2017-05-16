package com.jademcosta.starstore.transactionsList;


import com.jademcosta.starstore.database.TransactionsRepository;

public class TransactionsListModel implements TransactionsListContract.Model {

    private Presenter presenter;
    private TransactionsRepository transactionsRepository;

    public TransactionsListModel(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}
