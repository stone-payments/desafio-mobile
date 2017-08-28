package com.germano.desafiostone.services;

import com.germano.desafiostone.models.Product;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by germano on 27/08/17.
 */

public class ProductServiceImpl implements ProductService {

    @Override
    public Observable<List<Product>> getProductList() {
        IProductService service = RetrofitUtil.getInstance().apiBuild().create(IProductService.class);

        return service.getProductList()
                .observeOn(Schedulers.io())
                .map(response -> {
                    if (response != null) {
                        return response;
                    }
                    return null;
                });
    }
}
