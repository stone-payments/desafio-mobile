package com.jademcosta.starstore.cart;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jademcosta.starstore.R;
import com.jademcosta.starstore.entity.Item;
import com.jademcosta.starstore.itemsList.ItemsListAdapter;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CartContract.View {

    private Presenter presenter;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private TextView totalPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initializeViews();
        initializeListeners();

        CartInjector injector = new CartInjector();
        injector.inject(this, getApplicationContext());

        presenter.onCreate();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context.getApplicationContext(), CartActivity.class);
    }

    private void initializeViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.cart_screen_title));
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView = (RecyclerView) findViewById(R.id.activity_cart_recyclerview);
        totalPriceTextView = (TextView) findViewById(R.id.activity_cart_total_price_text);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initializeListeners() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(recyclerView, "Do something with me!", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setCartItems(List<Item> items) {
        ItemsListAdapter adapter = new ItemsListAdapter(items);
//        adapter.setOnClickListener(new ItemsListAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClicked(Item item) {
//                presenter.itemClicked(item);
//            }
//        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setCartItemsTotalPrice(String total) {
        totalPriceTextView.setText(total);
    }
}
