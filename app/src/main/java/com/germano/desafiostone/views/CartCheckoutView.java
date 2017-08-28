package com.germano.desafiostone.views;

/**
 * Created by germano on 27/08/17.
 */

public interface CartCheckoutView {

    void contentView();
    void showCardPayment();
    void setupRecyclerView();
    void showError(String msg);
    void deleteItemFromList(int integer);
}
