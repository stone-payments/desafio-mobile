package com.jademcosta.starstore.transactionsList;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.jademcosta.starstore.R;
import com.jademcosta.starstore.entity.Transaction;

import java.util.List;

public class TransactionsListActivity extends AppCompatActivity
        implements TransactionsListContract.View {

    private Presenter presenter;
    private RecyclerView recyclerView;

    public static Intent newIntent(Context context) {
        return new Intent(context.getApplicationContext(), TransactionsListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions_list);

        initializeViews();
     
        TransactionsListInjector injector = new TransactionsListInjector();
        injector.inject(this, getApplicationContext());

        presenter.onCreate();
    }

    @Override
    public void setTransactionsList(List<Transaction> transactions) {
        TransactionsListAdapter adapter = new TransactionsListAdapter(transactions);
        recyclerView.setAdapter(adapter);
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    private void initializeViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.transactions_list_screen_title));
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.activity_transactions_list_recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
}
