package com.stone.desafiomobile.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.stone.desafiomobile.model.Product
import com.stone.desafiomobile.model.PurchaseLog
import com.tcc.mensageria.database.DateConverter

/**
 * Classe para criar a conexão com o banco de dados
 * Define quais classes são mapeadas e fornece os DAOs
 */
@Database(entities = arrayOf(Product::class, PurchaseLog::class), version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Fornece dao para a classe [Product]
     */
    abstract fun getProductDAO(): ProductDAO


    /**
     * Fornece dao para a classe [PurchaseLog]
     */
    abstract fun getPurchaseLogDAO(): PurchaseLogDAO
}
