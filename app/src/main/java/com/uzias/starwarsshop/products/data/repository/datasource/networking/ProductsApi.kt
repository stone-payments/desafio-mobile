package com.uzias.starwarsshop.products.data.repository.datasource.networking

import com.uzias.starwarsshop.products.data.repository.datasource.networking.json.JsonProduct
import io.reactivex.Flowable
import retrofit2.http.GET

interface ProductsApi {

    @GET("products.json")
    fun getProducts(): Flowable<List<JsonProduct>>

}