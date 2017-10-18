package com.desafiostone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.desafiostone.R;
import com.desafiostone.adapter.CartAdapter;
import com.desafiostone.adapter.ProductAdapter;
import com.desafiostone.database.RealmDatabase;
import com.desafiostone.domain.Products;

import java.util.ArrayList;

import io.realm.RealmResults;

public class CartActivity extends AppCompatActivity {

    private String TAG = "TAG";

    private RecyclerView mRecyclerView;
    private CartAdapter mCartAdapter;
    private Toolbar mToolbar;

    private RealmResults<Products> mProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //TOOLBAR
        mToolbar = findViewById(R.id.tbCart);
        mToolbar.setTitle(getString(R.string.cart));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //RECYCLER VIEW
        mRecyclerView = findViewById(R.id.rvCart);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //REALM
        RealmDatabase.getInstance().setContext(this);
        mProducts = RealmDatabase.getInstance().getCart();

        //ADAPTER
        mCartAdapter = new CartAdapter(this, mProducts);
        mRecyclerView.setAdapter(mCartAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
