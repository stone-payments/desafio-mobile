package com.germano.desafiostone.presenters;

import com.germano.desafiostone.models.Product;
import com.germano.desafiostone.views.CartCheckoutView;

/**
 * Created by germano on 27/08/17.
 */

public interface CartCheckoutPresenter {
    void initView(CartCheckoutView cartCheckoutView);
    void onClickCheckout(int size);
    void onDeleteClicked(Object o);
    void updateView();
}
