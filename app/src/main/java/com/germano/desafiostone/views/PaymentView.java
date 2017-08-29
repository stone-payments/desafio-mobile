package com.germano.desafiostone.views;


/**
 * Created by germano on 28/08/17.
 */

public interface PaymentView {

    void contentView();
    void showLoading();
    void hideLoading();
    void showSuccessfulPayment();
    void callPaymentService();
}
