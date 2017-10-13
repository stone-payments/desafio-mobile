package com.stone.desafiomobile.network

import com.stone.desafiomobile.model.Product
import com.stone.desafiomobile.model.Purchase
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ProductsService {

    @GET("stone-pagamentos/desafio-mobile/master/products.json")
    fun getProducts(): Call<List<Product>>

    @POST()
    fun buyProducts(@Url url: String, @Body purchase: Purchase): Call<Map<String, String>>
}