package com.germano.desafiostone.presenters;

import com.germano.desafiostone.Singleton;
import com.germano.desafiostone.views.ProductDetailView;

/**
 * Created by germano on 26/08/17.
 */

public class ProductDetailPresenterImpl implements ProductDetailPresenter {

    private ProductDetailView mView;

    @Override
    public void initView(ProductDetailView productDetailView) {
        this.mView = productDetailView;

        if(mView != null){
            mView.contentView();
        }
    }

    @Override
    public void onCheckoutClicked() {
        mView.showCheckout();
    }

    @Override
    public void checkNotificationNumber(int size) {
        if(size != 0){
            mView.enableNotification(size);
        } else {
            mView.hideNotification();
        }
    }

    @Override
    public void onMenuCheckoutClicked() {
        mView.showCheckoutMenu();
    }
}
