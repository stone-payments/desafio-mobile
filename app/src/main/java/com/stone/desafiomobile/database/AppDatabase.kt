package com.stone.desafiomobile.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.stone.desafiomobile.model.Product

@Database(entities = arrayOf(Product::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getProductDAO(): ProductDAO
}
