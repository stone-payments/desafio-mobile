package com.hernand.starwars.controller;

import com.hernand.starwars.api.IProductService;
import com.hernand.starwars.model.Transact;
import com.hernand.starwars.model.dao.TransactDao;
import com.hernand.starwars.util.CartSingleton;
import com.hernand.starwars.vo.ProductVO;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nando on 29/05/2017.
 */

public class MainController {

    public MainController(TransactDao dao){
        transactionsDao = dao;
    }

    public MainController(){

    }

    private TransactDao transactionsDao = new TransactDao();

    public List<Transact> listTransactions(){
        return transactionsDao.listAll();
    }

    public List<ProductVO> getProdutos(){
        CartSingleton carrinho = CartSingleton.getInstance();
        return carrinho.getProdutos();
    }

    public void callApiRestProdutos(final Callback<List<ProductVO>> callback){
        OkHttpClient okHttpClient = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IProductService.API_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IProductService service = retrofit.create(IProductService.class);

        Call<List<ProductVO>> listCall = service.listProducts();

        listCall.enqueue(new Callback<List<ProductVO>>() {
            @Override
            public void onResponse(Call<List<ProductVO>> call, Response<List<ProductVO>> response) {

                callback.onResponse(call,response);
            }

            @Override
            public void onFailure(Call<List<ProductVO>> listCall, Throwable t) {
                callback.onFailure(listCall,t);

            }
        });
    }
}
