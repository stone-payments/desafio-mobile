package com.stone.desafiomobile.network

import com.stone.desafiomobile.model.Product
import retrofit2.Call
import retrofit2.http.GET

interface ProductsService {

    @GET("stone-pagamentos/desafio-mobile/master/products.json")
    fun getProducts(): Call<List<Product>>
}