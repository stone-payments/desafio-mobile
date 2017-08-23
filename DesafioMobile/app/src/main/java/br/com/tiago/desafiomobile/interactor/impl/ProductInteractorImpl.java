package br.com.tiago.desafiomobile.interactor.impl;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import br.com.tiago.desafiomobile.interactor.ProductInteractor;
import br.com.tiago.desafiomobile.model.ProductModel;
import br.com.tiago.desafiomobile.service.ProductService;
import br.com.tiago.desafiomobile.service.impl.ProductServiceImpl;
import br.com.tiago.desafiomobile.utils.FormatItemValue;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by TiagoCabral on 8/18/2017.
 */

public class ProductInteractorImpl implements ProductInteractor {

    private static String TAG = ProductInteractorImpl.class.getSimpleName();
    private ProductService productService;

    public ProductInteractorImpl() {
        this.productService = new ProductServiceImpl();
    }

    @Override
    public Observable<List<ProductModel>> getProducts() {
        Log.d(TAG, "getProducts: inicio");
        return productService.getProducts().map(new Func1<List<ProductModel>, List<ProductModel>>() {
            @Override
            public List<ProductModel> call(List<ProductModel> products) {
                for (ProductModel productModel : products) {
                    try {
                        productModel.setPrice((productModel.getPrice() / 100));
                        productModel.setBitmap(FormatItemValue.imageFormat(productModel.getThumbnailHd()));
                    } catch (IOException ioe) {
                        Log.e(TAG, "getProducts: ".concat(ioe.getLocalizedMessage().concat(productModel.getTitle())), ioe);
                    }
                }
                Log.d(TAG, "getProducts: fim");
                return products;
            }
        });
    }
}
