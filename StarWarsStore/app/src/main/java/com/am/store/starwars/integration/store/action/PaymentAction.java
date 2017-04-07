package com.am.store.starwars.integration.store.action;

import com.am.store.starwars.integration.store.vo.request.payment.PaymentRequestVO;
import com.am.store.starwars.integration.store.vo.response.payment.PaymentResponseVO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Augusto on 15/01/2017.
 */

public interface PaymentAction {

    @POST("/payment")
    public Call<PaymentResponseVO> performPayment(@Body PaymentRequestVO paymentRequestVO);
}