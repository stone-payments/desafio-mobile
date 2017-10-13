package com.stone.desafiomobile.di

import android.arch.persistence.room.Room
import android.content.Context
import com.stone.desafiomobile.database.AppDatabase
import com.stone.desafiomobile.database.ProductDAO
import com.stone.desafiomobile.database.PurchaseLogDAO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideDatabase(): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "desafio-mobile.db").build()
    }

    @Provides
    fun provideProductDao(appDatabase: AppDatabase): ProductDAO {
        return appDatabase.getProductDAO()
    }

    @Provides
    fun providePurchaseLogDao(appDatabase: AppDatabase): PurchaseLogDAO {
        return appDatabase.getPurchaseLogDAO()
    }
}
