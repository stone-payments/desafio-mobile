package com.am.store.starwars.integration.store.action;

import com.am.store.starwars.integration.store.vo.ProductVO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Augusto on 13/01/2017.
 */
public interface ProductAction {

    @GET("/stone-pagamentos/desafio-mobile/master/products.json")
    public Call<List<ProductVO>> getProducts();
}