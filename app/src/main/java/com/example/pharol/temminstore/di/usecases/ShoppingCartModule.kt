package com.example.pharol.temminstore.di.usecases

import com.example.pharol.temminstore.MyDatabase
import com.example.pharol.temminstore.shoppingcart.ShoppingCartDao
import com.example.pharol.temminstore.shoppingcart.ShoppingCartRepository
import dagger.Module
import dagger.Provides


@Module
class ShoppingCartModule {

    @dagger.Provides
    fun provideShoppingCartRepository(shoppingCartDao: ShoppingCartDao):
            ShoppingCartRepository = ShoppingCartRepository(shoppingCartDao)

    @dagger.Provides
    fun provideShoppingCartDao(myDatabase: MyDatabase): ShoppingCartDao = myDatabase.shoppingCartDao()
}