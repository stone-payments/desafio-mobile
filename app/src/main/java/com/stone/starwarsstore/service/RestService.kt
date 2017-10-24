package com.stone.starwarsstore.service

import com.google.gson.GsonBuilder
import com.stone.starwarsstore.model.Order
import com.stone.starwarsstore.model.Product
import com.stone.starwarsstore.model.Transaction
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable

class RestService {

    val service: ServiceAPI
    val BASE_URL = "https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/"
    val API_MOCK_URL = "https://private-667bc-danielgomes.apiary-mock.com/transaction"

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()

        service = retrofit.create<ServiceAPI>(ServiceAPI::class.java)
    }

    fun loadProducts(): Observable<Product> {
        return service.getProducts()
                .flatMap { productsResult -> Observable.from(productsResult) }
                .map { product ->
                    Product(product.title, product.price, product.seller, product.thumbnailHd, product.zipCode, product.date)
                }
    }

    fun saveTransaction(transaction: Transaction, success: (data: Map<String, Int>?) -> Unit) {
        service.saveTransaction(API_MOCK_URL, transaction).enqueue(object : Callback<Map<String, Int>?> {
            override fun onResponse(call: Call<Map<String, Int>?>, response: Response<Map<String, Int>?>) {
                success(response.body())
            }

            override fun onFailure(call: Call<Map<String, Int>?>, t: Throwable) {
                error(t)
            }
        })
    }
}