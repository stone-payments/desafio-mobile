package com.jademcosta.starstore.transactionsList;


import com.jademcosta.starstore.database.TransactionsRepository;
import com.jademcosta.starstore.entity.Transaction;

import java.util.List;

public class TransactionsListModel implements TransactionsListContract.Model {

    private Presenter presenter;
    private TransactionsRepository transactionsRepository;

    public TransactionsListModel(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public List<Transaction> getTransactionsList() {
        return transactionsRepository.getTransactions();
    }
}
