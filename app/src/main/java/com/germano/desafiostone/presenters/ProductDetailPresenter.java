package com.germano.desafiostone.presenters;

import com.germano.desafiostone.views.ProductDetailView;

/**
 * Created by germano on 26/08/17.
 */

public interface ProductDetailPresenter {

    void initView(ProductDetailView productDetailView);

    void onCheckoutClicked();

    void checkNotificationNumber(int size);

    void onMenuCheckoutClicked();
}
