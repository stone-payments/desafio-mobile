package com.stone.desafiomobile.network

import com.stone.desafiomobile.model.Product
import com.stone.desafiomobile.model.Purchase
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

    fun BuyProducts(purchase: Purchase,
                    success: (data: Map<String, String>?) -> Unit,
                    error: (t: Throwable) -> Unit) {
        productsService.buyProducts(" https://private-d496c4-renanbarros.apiary-mock.com/buy", purchase)
                .enqueue(object : Callback<Map<String, String>> {
                    override fun onResponse(call: Call<Map<String, String>>, response: Response<Map<String, String>>) {
                        success(response.body())
                    }

                    override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                        error(t)
                    }
                })
    }

}
