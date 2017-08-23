package br.com.tiago.desafiomobile.interactor;

import java.util.List;

import br.com.tiago.desafiomobile.model.ProductModel;
import rx.Observable;

/**
 * Created by TiagoCabral on 8/18/2017.
 */

public interface ProductInteractor {

    Observable<List<ProductModel>> getProducts();

}
