package com.stone.lfernandosantos.storewars.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.stone.lfernandosantos.storewars.R;
import com.stone.lfernandosantos.storewars.controlers.IProductsService;
import com.stone.lfernandosantos.storewars.controlers.ListProductsAdapter;
import com.stone.lfernandosantos.storewars.controlers.RecyclerViewOnClickListenerHack;
import com.stone.lfernandosantos.storewars.controlers.SupportStatus;
import com.stone.lfernandosantos.storewars.models.Product;

import java.util.List;
import java.util.concurrent.TimeUnit;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {



    SupportStatus status;
    List<Product> products;
    private FloatingActionButton btnHistorico;
    private FloatingActionButton btnCompras;

    private RecyclerView recyclerView;
    private ListProductsAdapter productsAdapter;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Store Wars");
        progressDialog = new ProgressDialog(this);

        btnHistorico = (FloatingActionButton) findViewById(R.id.fabOrder);
        btnCompras = (FloatingActionButton) findViewById(R.id.fabCartList);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm  = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        btnHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, HistoricoActivity.class));

            }
        });

        btnCompras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, CartActivity.class));

            }
        });


//        Client client = ClientBuilder.newClient();
//        Entity payload = Entity.json("{  'card_number': '1234123412341234',  'value': 7990,  'cvv': 787,  'card_holder_name': 'Lucas',  'exp_date': '12/19'}");
//        javax.ws.rs.core.Response response = client.target("https://private-f2124-storewars.apiary-mock.com/pay")
//                .request(MediaType.APPLICATION_JSON_TYPE)
//                .post(payload);
//
//        System.out.println("status: " + response.getStatus());
//        System.out.println("body:" + response.readEntity(String.class));


    }


    private void doRequest() {
        OkHttpClient okHttpClient = getRequestHeader();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IProductsService.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IProductsService json = retrofit.create(IProductsService.class);

        Call<List<Product>> listaRequest = json.listCall();

        listaRequest.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                products = response.body();

                productsAdapter = new ListProductsAdapter(products, HomeActivity.this);
                productsAdapter.setmRecyclerViewOnClickListenerHack(HomeActivity.this);
                recyclerView.setAdapter(productsAdapter);

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

                progressDialog.dismiss();

                Snackbar.make(btnCompras, "Sem Conexão!", Snackbar.LENGTH_SHORT).show();

                Log.i("JSON", "ERRO" + t.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        status = new SupportStatus(this);
        if(!status.getStatusInternet()){

            Snackbar.make(btnCompras, "Sem Conexão!", Snackbar.LENGTH_SHORT).show();

        }else {

            progressDialog.setMessage("Aguarde...");
            progressDialog.show();

            doRequest();
        }
    }

    @Override
    public void onClickListener(View view, int position) {

        Intent intentDetails = new Intent(HomeActivity.this, DetailActivity.class);
        intentDetails.putExtra("product", products.get(position));
        startActivity(intentDetails);

    }

    private OkHttpClient getRequestHeader() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        return okHttpClient;
    }

}
