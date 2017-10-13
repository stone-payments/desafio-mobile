package com.stone.desafiomobile.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.stone.desafiomobile.model.Product
import com.stone.desafiomobile.model.PurchaseLog
import com.tcc.mensageria.database.DateConverter

@Database(entities = arrayOf(Product::class, PurchaseLog::class), version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getProductDAO(): ProductDAO

    abstract fun getPurchaseLogDAO(): PurchaseLogDAO
}
