package com.stone.desafiomobile.network

import com.stone.desafiomobile.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RestRepository @Inject constructor(
        private val productsService: ProductsService) {

    fun getProducts(success: (data: List<Product>?) -> Unit,
                    error: (t: Throwable) -> Unit) {
        productsService.getProducts().enqueue(object : Callback<List<Product>> {

            override fun onResponse(call: Call<List<Product>>,
                                    response: Response<List<Product>>) {
                success(response.body())
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                error(t)
            }
        })
    }
}
