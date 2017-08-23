package br.com.tiago.desafiomobile.repository.remote.endpoint;

import java.util.List;

import br.com.tiago.desafiomobile.model.ProductModel;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by TiagoCabral on 8/18/2017.
 */

public interface ProductEndPoint {

    @GET("products.json")
    rx.Observable<List<ProductModel>> getProducts();
}
