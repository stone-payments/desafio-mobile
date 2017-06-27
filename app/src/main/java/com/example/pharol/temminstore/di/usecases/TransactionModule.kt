package com.example.pharol.temminstore.di.usecases

import android.app.Application
import com.example.pharol.temminstore.MyDatabase
import com.example.pharol.temminstore.shoppingcart.ShoppingCartDao
import com.example.pharol.temminstore.transaction.TransactionDAO
import com.example.pharol.temminstore.transaction.TransactionRepository
import dagger.Module
import dagger.Provides
import com.example.pharol.temminstore.WebService
import javax.inject.Singleton


@Module
class TransactionModule{

    @Provides
    fun provideTransactionRepository(webService: WebService, transactionDAO: TransactionDAO, cartDAO: ShoppingCartDao, application: Application):
            TransactionRepository = TransactionRepository(webService, transactionDAO, cartDAO, application)

    @Provides @Singleton
    fun provideTransactionDao(myDatabase: MyDatabase): TransactionDAO = myDatabase.transactionDao()
}