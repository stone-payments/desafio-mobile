package com.example.pharol.temminstore.di.usecases

import android.app.Application
import com.example.pharol.temminstore.MyDatabase
import dagger.Module
import dagger.Provides
import com.example.pharol.temminstore.item.ItemDAO
import com.example.pharol.temminstore.item.ItemRepository
import com.example.pharol.temminstore.WebService


@Module
class ItemModule{
    @Provides
    fun provideItemRepository(webService: WebService, itemDao: ItemDAO, application: Application):
            ItemRepository = ItemRepository(webService,itemDao,application)

    @Provides
    fun provideItemDao(myDatabase: MyDatabase): ItemDAO = myDatabase.itemDao()
}