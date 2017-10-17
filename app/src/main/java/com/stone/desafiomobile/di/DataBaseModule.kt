package com.stone.desafiomobile.di

import android.arch.persistence.room.Room
import android.content.Context
import com.stone.desafiomobile.database.AppDatabase
import com.stone.desafiomobile.database.ProductDAO
import com.stone.desafiomobile.database.PurchaseLogDAO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Classe de injeção de dependência para objetos relacionados ao banco de dados
 * @param context Contexto da aplicação para criar instância do banco de dados
 */
@Module
class DatabaseModule(private val context: Context) {

    /**
     * @return Instancia de [AppDatabase]
     */
    @Singleton
    @Provides
    fun provideDatabase(): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "desafio-mobile.db").build()
    }

    /**
     * @return Instancia de [ProductDAO]
     */
    @Provides
    fun provideProductDao(appDatabase: AppDatabase): ProductDAO {
        return appDatabase.getProductDAO()
    }

    /**
     * @return Instancia de [PurchaseLogDAO]
     */
    @Provides
    fun providePurchaseLogDao(appDatabase: AppDatabase): PurchaseLogDAO {
        return appDatabase.getPurchaseLogDAO()
    }
}
