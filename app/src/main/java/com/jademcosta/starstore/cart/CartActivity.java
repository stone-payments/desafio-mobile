package com.jademcosta.starstore.cart;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jademcosta.starstore.R;
import com.jademcosta.starstore.entity.Item;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CartContract.View {

    private Presenter presenter;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private TextView totalPriceTextView;
    private TextView emptyViewText;

    public static Intent newIntent(Context context) {
        return new Intent(context.getApplicationContext(), CartActivity.class);
    }

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

    private void initializeViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.cart_screen_title));
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView = (RecyclerView) findViewById(R.id.activity_cart_recyclerview);
        totalPriceTextView = (TextView) findViewById(R.id.activity_cart_total_price_text);
        emptyViewText = (TextView) findViewById(R.id.activity_cart_empty_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initializeListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.checkoutClicked(CartActivity.this);
            }
        });
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setCartItems(List<Item> items) {
        CartAdapter adapter = new CartAdapter(items);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setCartItemsTotalPrice(String total) {
        totalPriceTextView.setText(total);
    }

    @Override
    public void showEmptyState() {
        fab.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        totalPriceTextView.setVisibility(View.GONE);
        emptyViewText.setVisibility(View.VISIBLE);
    }
}
