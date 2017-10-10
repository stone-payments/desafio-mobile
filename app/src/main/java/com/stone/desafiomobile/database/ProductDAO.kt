package com.stone.desafiomobile.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.stone.desafiomobile.model.Product

@Dao
interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg data: Product): LongArray

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBatch(data: List<Product>): LongArray

    @Query("SELECT * FROM products")
    fun listAll(): LiveData<List<Product>>

    @Query("SELECT * FROM products where id= :arg0")
    fun findById(id: Long): LiveData<Product>
}
