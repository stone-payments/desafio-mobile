package com.desafiostone.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.desafiostone.R;
import com.desafiostone.adapter.ProductAdapter;
import com.desafiostone.api.ProductsAPI;
import com.desafiostone.database.RealmDatabase;
import com.desafiostone.domain.Products;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ProductAdapter mProductAdapter;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TOOLBAR
        mToolbar = findViewById(R.id.tbMain);
        mToolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(mToolbar);

        //RECYCLER VIEW
        mRecyclerView = findViewById(R.id.rvProducts);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //PEGANDO TODAS AS INFORMAÇÕES DO JSON
        ProductsAPI.ProductsInterface productsInterface = ProductsAPI.getClient();
        Call<ArrayList<Products>> call = productsInterface.getProducts();
        call.enqueue(new Callback<ArrayList<Products>>() {
            @Override
            public void onResponse(Call<ArrayList<Products>> call, Response<ArrayList<Products>> response) {
                try {
                    if (response.body() != null) {
                        ArrayList<Products> mProducts = response.body();

                        mProductAdapter = new ProductAdapter(MainActivity.this, mProducts);
                        mRecyclerView.setAdapter(mProductAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Products>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //REALM
        RealmDatabase.getInstance().setContext(this);

        if (id == R.id.seeCart) {
            if (RealmDatabase.getInstance().isEmptyCart()) {
                Toast.makeText(this, "O carrinho está vazio! :(", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(this, CartActivity.class));
            }
        } else if (id == R.id.transactions) {
            if (RealmDatabase.getInstance().isEmptyTransactions()) {
                Toast.makeText(this, "Você não possui transições, vamos comprar! :D", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(this, TransactionsActivity.class));
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
