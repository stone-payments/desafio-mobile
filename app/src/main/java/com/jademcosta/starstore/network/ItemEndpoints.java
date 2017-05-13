package com.jademcosta.starstore.network;


import com.jademcosta.starstore.entity.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ItemEndpoints {

    @GET("/stone-pagamentos/desafio-mobile/master/products.json")
    Call<List<Item>> listItems();
}
