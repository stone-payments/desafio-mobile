package com.stone.desafiomobile.di

import com.stone.desafiomobile.network.ProductsService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Classe de injeção de dependêcia para objetos relacionados ao retrofit
 */
@Module
class RetrofitModule() {

    /**
     * @return Instancia configurada do [Retrofit]
     */
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    /**
     * @return Instancia do [ProductsService]
     */
    @Provides
    fun provideProductsService(retrofit: Retrofit): ProductsService{
        return retrofit.create(ProductsService::class.java)
    }
}