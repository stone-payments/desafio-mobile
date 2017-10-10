package br.com.wagnerrodrigues.starwarsstore.data.service

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


open class RestService {

    private val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .excludeFieldsWithoutExposeAnnotation()
            .create()!!

    var productsBuilder = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()!!

    var serviceBuilder = Retrofit.Builder()
            .baseUrl("https://private-015da-starwarsstore.apiary-mock.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()!!

}