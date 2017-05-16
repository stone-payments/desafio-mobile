package com.jademcosta.starstore.transactionsList;


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
}
