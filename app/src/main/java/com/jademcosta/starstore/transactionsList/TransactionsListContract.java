package com.jademcosta.starstore.transactionsList;


import android.content.Context;

import com.jademcosta.starstore.entity.Transaction;

import java.util.List;

public interface TransactionsListContract {

    interface View {

        void setTransactionsList(List<Transaction> transactions);

        interface Presenter {
            void onCreate();
            void navigateToHomeClicked(Context context);
        }
    }

    interface Model {

        List<Transaction> getTransactionsList();

        interface Presenter {

        }
    }
}
