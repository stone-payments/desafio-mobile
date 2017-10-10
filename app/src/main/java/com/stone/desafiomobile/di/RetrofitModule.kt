package com.stone.desafiomobile.di

import android.content.Context
import android.preference.PreferenceManager
import com.stone.desafiomobile.R
import com.stone.desafiomobile.network.ProductsService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule() {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    fun provideProductsService(retrofit: Retrofit): ProductsService{
        return retrofit.create(ProductsService::class.java)
    }
}