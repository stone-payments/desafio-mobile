package com.desafiostone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.desafiostone.R;
import com.desafiostone.adapter.CartAdapter;
import com.desafiostone.api.APIAry;
import com.desafiostone.database.RealmDatabase;
import com.desafiostone.domain.Products;
import com.desafiostone.domain.Purchase;
import com.desafiostone.domain.Transaction;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.purchase) {
            APIAry.APIAryInterface apiAryInterface = APIAry.getClient();
            Call<Purchase> call = apiAryInterface.finishPurchase();
            call.enqueue(new Callback<Purchase>() {
                @Override
                public void onResponse(Call<Purchase> call, Response<Purchase> response) {
                    try {
                        if (response.body() != null) {
                            RealmDatabase.getInstance().setContext(CartActivity.this);

                            Transaction transaction = new Transaction();
                            transaction.setValue(response.body().getValue());
                            transaction.setCard_holder_name(response.body().getCard_holder_name());
                            transaction.setLast_digits(response.body().getCard_number().substring(12));

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            String currentDateandTime = sdf.format(new Date());
                            transaction.setDate_hour(currentDateandTime);

                            RealmDatabase.getInstance().saveTransaction(transaction);
                        } else {
                            Log.i(TAG, "onResponse: " + response.body());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Purchase> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }

        return true;
    }


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
