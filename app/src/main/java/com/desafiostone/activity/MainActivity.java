package com.desafiostone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.desafiostone.R;
import com.desafiostone.adapter.ProductAdapter;
import com.desafiostone.api.ProductsAPI;
import com.desafiostone.domain.Products;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String TAG = "TAG";

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
}
