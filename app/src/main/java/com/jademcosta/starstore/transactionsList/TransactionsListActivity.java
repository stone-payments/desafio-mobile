package com.jademcosta.starstore.transactionsList;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jademcosta.starstore.R;
import com.jademcosta.starstore.creditCard.CreditCardInjector;

public class TransactionsListActivity extends AppCompatActivity
        implements TransactionsListContract.View {

    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        TransactionsListInjector injector = new TransactionsListInjector();
        injector.inject(this, getApplicationContext());
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}
