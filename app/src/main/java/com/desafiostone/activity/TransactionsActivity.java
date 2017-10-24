package com.desafiostone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.desafiostone.R;
import com.desafiostone.adapter.ProductAdapter;
import com.desafiostone.adapter.TransactionsAdapter;
import com.desafiostone.database.RealmDatabase;
import com.desafiostone.domain.Transaction;

import io.realm.RealmResults;

public class TransactionsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TransactionsAdapter mTransactionsAdapter;
    private Toolbar mToolbar;

    private RealmResults<Transaction> mTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        //TOOLBAR
        mToolbar = findViewById(R.id.tbTransactions);
        mToolbar.setTitle(getString(R.string.transactions));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //RECYCLER VIEW
        mRecyclerView = findViewById(R.id.rvTransactions);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //REALM
        RealmDatabase.getInstance().setContext(this);
        mTransactions = RealmDatabase.getInstance().getTransactions();

        //  ADAPTER
        mTransactionsAdapter = new TransactionsAdapter(this, mTransactions);
        mRecyclerView.setAdapter(mTransactionsAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
}
