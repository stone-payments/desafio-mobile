package com.jademcosta.starstore.itemsList;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

    private void initializeListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initializeViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView = (RecyclerView) findViewById(R.id.activity_items_list_recyclerview);
        loadingView = (ProgressBar) findViewById(R.id.activity_items_list_loading_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
}
