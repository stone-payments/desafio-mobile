package com.jademcosta.starstore.transactionsList;


import com.jademcosta.starstore.entity.Transaction;

import java.util.List;

public interface TransactionsListContract {

    interface View {

        void setTransactionsList(List<Transaction> transactions);

        interface Presenter {
            void onCreate();
        }
    }

    interface Model {

        List<Transaction> getTransactionsList();

        interface Presenter {

        }
    }
}
