package com.example.pharol.temminstore.di

import android.app.Application
import android.arch.persistence.room.Room
import com.example.pharol.temminstore.MyDatabase
import dagger.Module
import dagger.Provides
import com.example.pharol.temminstore.WebService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class DataSourceModule{
    @Provides @Singleton
    fun provideWebService(): WebService = Retrofit.Builder().
            baseUrl("https://raw.githubusercontent.com/").
            addConverterFactory(GsonConverterFactory.create()).build().create(WebService::class.java)

    @Provides @Singleton
    fun provideDatabase(mApplication: Application): MyDatabase = Room.databaseBuilder(mApplication, MyDatabase::class.java,"temminstoreDB").build()

}