package br.com.tiago.desafiomobile.service.impl;

import java.util.List;

import br.com.tiago.desafiomobile.model.ProductModel;
import br.com.tiago.desafiomobile.repository.remote.RetrofitConnection;
import br.com.tiago.desafiomobile.repository.remote.endpoint.ProductEndPoint;
import br.com.tiago.desafiomobile.service.ProductService;
import rx.Observable;

/**
 * Created by TiagoCabral on 8/18/2017.
 */

public class ProductServiceImpl implements ProductService {

    @Override
    public Observable<List<ProductModel>> getProducts() {
        return RetrofitConnection.connect(ProductEndPoint.class, RetrofitConnection.GET_URL).getProducts();
    }
}
