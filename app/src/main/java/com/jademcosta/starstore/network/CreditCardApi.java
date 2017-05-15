package com.jademcosta.starstore.network;


import com.jademcosta.starstore.entity.Payment;

import retrofit2.Callback;

public class CreditCardApi {
    private CreditCardEndpoints endpoints;

    public CreditCardApi() {
        endpoints =
                NetworkStackBuilder.getInstance().getServiceGenerator().create(CreditCardEndpoints.class);
    }

    public void postPayment(Payment payment, Callback<Void> callback) {
        endpoints.postPayment(payment).enqueue(callback);
    }
}
