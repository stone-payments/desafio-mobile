package com.desafiostone.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private CartAdapter mCartAdapter;
    private Toolbar mToolbar, mToolbarBottom;
    private Button btnBuy;
    private TextView tvTotal;

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

        //TOOLBAR BOTTOM
        mToolbarBottom = findViewById(R.id.incTbBottom);
        tvTotal = mToolbarBottom.findViewById(R.id.tvTotal);

        btnBuy = mToolbarBottom.findViewById(R.id.btnBuy);
        btnBuy.setOnClickListener(this);

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

        //TOTAL
        int value = Integer.valueOf(mProducts.get(0).getPrice());
        if (mProducts.size() > 1) {
            for (int i = 1; i < mProducts.size(); i++) {
                value = value + Integer.valueOf(mProducts.get(i).getPrice());
            }
        }
        tvTotal.setText(String.valueOf(value));
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

    @Override
    public void onClick(View v) {
        new MaterialDialog.Builder(this)
                .title(getResources().getString(R.string.warning))
                .content(getResources().getString(R.string.complete_purchase))
                .positiveText(getResources().getString(R.string.yes))
                .positiveColor(getResources().getColor(R.color.colorPrimaryText))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        APIAry.APIAryInterface apiAryInterface = APIAry.getClient();
                        Call<Purchase> call = apiAryInterface.finishPurchase();
                        call.enqueue(new Callback<Purchase>() {
                            @Override
                            public void onResponse(Call<Purchase> call, Response<Purchase> response) {
                                try {
                                    if (response.body() != null) {
                                        RealmDatabase.getInstance().setContext(CartActivity.this);

                                        Transaction transaction = new Transaction();

                                        int value = Integer.valueOf(mProducts.get(0).getPrice());
                                        if (mProducts.size() > 1) {
                                            for (int i = 1; i < mProducts.size(); i++) {
                                                value = value + Integer.valueOf(mProducts.get(i).getPrice());
                                            }
                                        }
                                        transaction.setValue(value);
                                        transaction.setCard_holder_name(response.body().getCard_holder_name());
                                        transaction.setLast_digits(response.body().getCard_number().substring(12));

                                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                        String currentDateandTime = sdf.format(new Date());
                                        transaction.setDate_hour(currentDateandTime);

                                        RealmDatabase.getInstance().saveTransaction(transaction);
                                        RealmDatabase.getInstance().removeAllItemFromCart();
                                        finish();
                                        Toast.makeText(CartActivity.this, "Compra finalizada com sucesso!", Toast.LENGTH_SHORT).show();
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
                })
                .negativeText(getResources().getString(R.string.no))
                .negativeColor(getResources().getColor(R.color.colorPrimaryText))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
