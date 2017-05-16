package com.jademcosta.starstore.itemsList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.jademcosta.starstore.R;
import com.jademcosta.starstore.entity.Item;

import java.util.List;

public class ItemsListActivity extends AppCompatActivity implements ItemsListContract.View {

    private Presenter presenter;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private ProgressBar loadingView;
    private BottomNavigationView bottomNavigationView;
    private View errorView;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), ItemsListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        ItemsListInjector injector = new ItemsListInjector();
        injector.inject(this, getApplicationContext());

        initializeViews();
        initializeListeners();

        presenter.onCreate();
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void hideList() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showList() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setListItems(List<Item> items) {
        ItemsListAdapter adapter = new ItemsListAdapter(items);
        adapter.setOnClickListener(new ItemsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(Item item) {
                presenter.itemClicked(item);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void informItemAddedToCart() {
        Snackbar.make(recyclerView, getString(R.string.items_list_added_to_cart), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showErrorView() {
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorView() {
        errorView.setVisibility(View.GONE);
    }

    private void initializeListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.goToCartButtonClicked(ItemsListActivity.this);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_main:
                        return false;
                    case R.id.action_transactions:
                        presenter.navigateToTransactionsClicked(ItemsListActivity.this);
                        return false;

                }
                return false;
            }
        });
    }

    private void initializeViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.items_list_screen_title));
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView = (RecyclerView) findViewById(R.id.activity_items_list_recyclerview);
        loadingView = (ProgressBar) findViewById(R.id.activity_items_list_loading_view);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        errorView = findViewById(R.id.activity_items_list_error_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
}
