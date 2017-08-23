package br.com.tiago.desafiomobile.service;

import java.util.List;

import br.com.tiago.desafiomobile.model.ProductModel;

/**
 * Created by TiagoCabral on 8/18/2017.
 */

public interface ProductService {
    rx.Observable<List<ProductModel>> getProducts();
}
