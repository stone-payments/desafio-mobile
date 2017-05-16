package com.jademcosta.starstore.network;


import com.jademcosta.starstore.entity.Payment;

import retrofit2.Callback;

public class CreditCardApi {

    private static final String BASE_URL = "https://private-aca45-stonejade.apiary-mock.com";

    private CreditCardEndpoints endpoints;

    public CreditCardApi() {
        endpoints =
                new NetworkStackBuilder(BASE_URL).getServiceGenerator().create(CreditCardEndpoints.class);
    }

    public void postPayment(Payment payment, Callback<Void> callback) {
        endpoints.postPayment(payment).enqueue(callback);
    }
}
