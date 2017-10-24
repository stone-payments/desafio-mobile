package com.stone.starwarsstore.service

import com.stone.starwarsstore.model.Order
import com.stone.starwarsstore.model.Product
import com.stone.starwarsstore.model.Transaction
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url
import rx.Observable

interface ServiceAPI {

    @GET("products.json")
    fun getProducts(): Observable<List<Product>?>

    @POST
    fun saveTransaction(@Url url: String, @Body transaction: Transaction): Call<Map<String, Int>?>
}