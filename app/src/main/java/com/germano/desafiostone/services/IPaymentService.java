package com.germano.desafiostone.services;

import com.germano.desafiostone.models.Payment;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by germano on 28/08/17.
 */

public interface IPaymentService {
    @POST("payment")
    Observable<Payment> doPayment(@Body Payment payment);
}
