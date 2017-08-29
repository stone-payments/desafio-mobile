package com.germano.desafiostone.services;

import com.germano.desafiostone.models.Product;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by germano on 27/08/17.
 */

public interface ProductService {

    Observable<List<Product>> getProductList();
}
