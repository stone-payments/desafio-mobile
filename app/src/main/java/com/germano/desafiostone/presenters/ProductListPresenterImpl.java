package com.germano.desafiostone.presenters;

import android.content.Context;
import android.util.Log;

import com.germano.desafiostone.R;
import com.germano.desafiostone.models.Product;
import com.germano.desafiostone.services.ProductService;
import com.germano.desafiostone.services.ProductServiceImpl;
import com.germano.desafiostone.views.ProductListView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by germano on 26/08/17.
 */

public class ProductListPresenterImpl implements ProductListPresenter {

    ProductListView mView;
    ProductService mService;
    Context mContext;

    public ProductListPresenterImpl(Context context) {
        this.mService = new ProductServiceImpl();
        this.mContext = context;
    }

    @Override
    public void initView(ProductListView productListView) {
        this.mView = productListView;

        if(mView != null){
            mView.contentView();
            getProductList();
        }
    }

    @Override
    public void onProductClicked(Product product) {
        mView.showProductDetail(product);
    }

    @Override
    public void getProductList() {
        mView.showLoading();
        Observable<List<Product>> mObservableProduct = mService.getProductList();

        mObservableProduct.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        retorno -> {
                            mView.hideLoading();
                            if (mView == null) return;
                            mView.setupRecyclerView(retorno);
                        },
                        error -> {
                            mView.hideLoading();
                            mView.showError(mContext.getString(R.string.error));
                        });
    }
}
