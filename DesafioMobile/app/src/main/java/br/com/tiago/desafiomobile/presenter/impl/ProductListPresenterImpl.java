package br.com.tiago.desafiomobile.presenter.impl;

import android.util.Log;

import java.util.List;

import br.com.tiago.desafiomobile.interactor.ProductInteractor;
import br.com.tiago.desafiomobile.model.ProductModel;
import br.com.tiago.desafiomobile.presenter.ProductListPresenter;
import br.com.tiago.desafiomobile.ui.fragment.ProductListFragment;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by TiagoCabral on 8/18/2017.
 */

public class ProductListPresenterImpl implements ProductListPresenter {

    private static String TAG = ProductListPresenterImpl.class.getSimpleName();
    private ProductListFragment mView;
    private ProductInteractor interactor;

    public ProductListPresenterImpl(ProductListFragment mView, ProductInteractor interactor) {
        this.mView = mView;
        this.interactor = interactor;
    }

    @Override
    public void getProductList() {

        interactor.getProducts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ProductModel>>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        Log.d(TAG, "onStart: ");
                        mView.showToast("Carregando...", 1);
                        mView.showToast("Carregando...", 1);
                        mView.showToast("Carregando...", 1);
                        mView.showToast("Carregando...", 1);
                        mView.showToast("Carregando...", 1);
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "carregarLista onError ", e);
                        mView.showToast("Erro ao carregar lista de produtos!", 0);
                    }

                    @Override
                    public void onNext(List<ProductModel> products) {
                        Log.d(TAG, "onNext: inicio");
                        if (products != null) {
//                            products.forEach(v -> {
//                                Log.i(TAG, "onNext: " + v.toString());
//                            });
                            mView.addAll(products);
                        }
                    }
                });
    }

    @Override
    public void onItemClick(int position) {
        mView.onItemClick(position);
    }
}
