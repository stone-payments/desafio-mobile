package com.germano.desafiostone.presenters;

import com.germano.desafiostone.Singleton;
import com.germano.desafiostone.views.CartCheckoutView;

/**
 * Created by germano on 27/08/17.
 */

public class CartCheckoutPresenterImpl implements CartCheckoutPresenter {

    CartCheckoutView mView;

    @Override
    public void initView(CartCheckoutView cartCheckoutView) {
        this.mView = cartCheckoutView;

        if(mView != null){
            mView.contentView();
            mView.setupRecyclerView();
        }
    }

    @Override
    public void onClickCheckout(int size) {
        if(size != 0){
            mView.showCardPayment();
        } else {
            mView.showError("Seu carrinho está vazio!");
        }
    }

    @Override
    public void onDeleteClicked(Object o) {
        mView.deleteItemFromList((Integer) o);
    }

    @Override
    public void updateView() {
        mView.contentView();
    }
}
