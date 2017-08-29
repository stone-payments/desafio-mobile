package com.germano.desafiostone.services;

import com.germano.desafiostone.models.Payment;

import io.reactivex.Observable;

/**
 * Created by germano on 28/08/17.
 */

public interface PaymentService {

    Observable<Payment> doPayment(Payment payment);
}
