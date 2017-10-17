package com.stone.desafiomobile.network

import com.stone.desafiomobile.model.Product
import com.stone.desafiomobile.model.Purchase
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * Classe que define quais as requisições serão realizadas para a API
 */
interface ProductsService {

    /**
     * Recupera a lista de produtos para comprar
     * @return resposta com lista de produtos
     */
    @GET("stone-pagamentos/desafio-mobile/master/products.json")
    fun getProducts(): Call<List<Product>>

    /**
     * Realiza a compra
     * @param url url definida manualmente por ser um endereço diferente da url base
     * @param purchase corpo da requisição
     * @return map com resposta da requisição
     */
    @POST()
    fun buyProducts(@Url url: String, @Body purchase: Purchase): Call<Map<String, String>>
}