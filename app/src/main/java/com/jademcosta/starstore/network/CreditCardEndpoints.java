package com.jademcosta.starstore.network;


import com.jademcosta.starstore.entity.Payment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CreditCardEndpoints {

    @POST("/payment")
    Call<Void> postPayment(@Body Payment payment);
}
