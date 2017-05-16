package com.jademcosta.starstore.transactionsList;


import com.jademcosta.starstore.entity.Transaction;

import java.util.List;

public class TransactionsListPresenter implements TransactionsListContract.View.Presenter,
        TransactionsListContract.Model.Presenter {

    private TransactionsListContract.Model model;
    private TransactionsListContract.View view;


    public void setModel(TransactionsListContract.Model model) {
        this.model = model;
    }

    public void setView(TransactionsListContract.View view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        List<Transaction> transactions = model.getTransactionsList();
        view.setTransactionsList(transactions);
    }
}
