package com.example.pharol.temminstore

import com.example.pharol.temminstore.item.Item
import com.example.pharol.temminstore.transaction.Transaction
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface WebService {

    @GET("/stone-pagamentos/desafio-mobile/master/products.json")
    fun getItemList() : Call<List<Item>>

    @POST("https://private-6d817f-temminstore.apiary-mock.com/buy")
    fun buyItem(@Body transaction: Transaction): Call<Transaction>

}