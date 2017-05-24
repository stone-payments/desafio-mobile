package com.stone.lfernandosantos.storewars.controlers;

import com.stone.lfernandosantos.storewars.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by lf.fernandodossantos on 20/05/17.
 */

public interface IProductsService {

    public final String BASE_URL = "https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/";

    @GET("products.json")
    Call<List<Product>> listCall();
}

