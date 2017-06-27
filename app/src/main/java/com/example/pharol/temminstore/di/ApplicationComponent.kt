    package com.example.pharol.temminstore.di

import android.app.Application
import com.example.pharol.temminstore.MyDatabase
import com.example.pharol.temminstore.di.usecases.ItemModule
import com.example.pharol.temminstore.di.usecases.ShoppingCartModule
import com.example.pharol.temminstore.shoppingcart.ShoppingCartDao
import com.example.pharol.temminstore.shoppingcart.ShoppingCartRepository
import com.example.pharol.temminstore.transaction.TransactionDAO
import com.example.pharol.temminstore.transaction.TransactionRepository
import dagger.Component
import com.example.pharol.temminstore.di.usecases.TransactionModule
import com.example.pharol.temminstore.TemminApplication
import pharol.com.br.temminstore.di.ApplicationModule
import com.example.pharol.temminstore.item.ItemDAO
import com.example.pharol.temminstore.item.ItemRepository
import com.example.pharol.temminstore.WebService
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class,
        DataSourceModule::class,
        ItemModule::class,
        ShoppingCartModule::class,
        TransactionModule::class))
interface ApplicationComponent{

    fun inject(temminApp: TemminApplication)

    fun getApplication() : Application

    fun getWebService(): WebService

    fun getDatabase(): MyDatabase

    fun getItemRepository(): ItemRepository

    fun getShoppingCartRepository(): ShoppingCartRepository

    fun getTransactionRepository(): TransactionRepository

    fun getShoppingCartDao(): ShoppingCartDao

    fun getItemDao(): ItemDAO

    fun getTransactionDao(): TransactionDAO

}