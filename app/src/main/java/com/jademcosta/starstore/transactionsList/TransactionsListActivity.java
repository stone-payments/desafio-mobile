package com.jademcosta.starstore.transactionsList;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.jademcosta.starstore.R;

public class TransactionsListActivity extends AppCompatActivity
        implements TransactionsListContract.View {

    private Presenter presenter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        initializeViews();
     
        TransactionsListInjector injector = new TransactionsListInjector();
        injector.inject(this, getApplicationContext());
    }

    private void initializeViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.transactions_list_screen_title));
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.activity_transactions_list_recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }


    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}
