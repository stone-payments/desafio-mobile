package com.germano.desafiostone.services;

import com.germano.desafiostone.models.Product;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by germano on 27/08/17.
 */

public interface IProductService {
    @GET("desafio-mobile/master/products.json")
    Observable<List<Product>> getProductList();
}
