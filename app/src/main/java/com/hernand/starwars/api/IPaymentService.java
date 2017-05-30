package com.hernand.starwars.api;

import com.hernand.starwars.vo.TransactionVO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Nando on 28/05/2017.
 */

public interface IPaymentService {

    public final String API_URL = "https://private-96027-starwars4.apiary-mock.com/";

    @Headers("Content-Type: application/json")
    @POST("products")
    Call<TransactionVO> doPayment(@Body TransactionVO body);


}
