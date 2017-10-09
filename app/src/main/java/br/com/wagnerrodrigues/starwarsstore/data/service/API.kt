package br.com.wagnerrodrigues.starwarsstore.data.service

import br.com.wagnerrodrigues.starwarsstore.domain.entity.PaymentResponse
import br.com.wagnerrodrigues.starwarsstore.domain.entity.Product
import br.com.wagnerrodrigues.starwarsstore.domain.entity.Transaction
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT


interface API {

    @GET("products.json")
    fun obterProdutos() : Call<List<Product>>

    @PUT("pay")
    fun sendPaymentData(@Body transaction: Transaction): Call<PaymentResponse>
}
