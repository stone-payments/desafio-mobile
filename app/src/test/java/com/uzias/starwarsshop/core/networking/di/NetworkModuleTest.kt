package com.uzias.starwarsshop.core.networking.di

import com.uzias.starwarsshop.products.data.repository.datasource.networking.ProductsApi
import com.uzias.starwarsshop.transactions.data.datasource.networking.TransactionsApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
class NetworkModuleTest {

    @Provides
    fun providesRxJava2CallAdapter(): RxJava2CallAdapterFactory
            = RxJava2CallAdapterFactory.create()

    @Provides
    fun providesGsonConverterFactory(): GsonConverterFactory
            = GsonConverterFactory.create()

    @Provides
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    fun providesRetrofit(rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
                         @Named("baseUrlTest") baseUrl: String, okHttpClient: OkHttpClient,
                         gsonConverterFactory: GsonConverterFactory): Retrofit
            = Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(baseUrl)
            .build()

    @Provides fun providesProductsApi(retrofit: Retrofit): ProductsApi
            = retrofit.create(ProductsApi::class.java)

    @Provides fun providesTransactionsApi(retrofit: Retrofit): TransactionsApi
            = retrofit.create(TransactionsApi::class.java)
}