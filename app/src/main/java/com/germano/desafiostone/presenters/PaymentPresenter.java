package com.germano.desafiostone.presenters;

import com.germano.desafiostone.models.Payment;
import com.germano.desafiostone.views.PaymentView;

/**
 * Created by germano on 28/08/17.
 */

public interface PaymentPresenter {

    void initView(PaymentView paymentView);
    void onPaymentClicked();
    void doPaymentService(Payment payment);

}
