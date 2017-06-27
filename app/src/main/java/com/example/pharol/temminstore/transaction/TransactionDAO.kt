package com.example.pharol.temminstore.transaction

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface TransactionDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(transaction: Transaction)

    @Delete
    fun delete(transaction: Transaction)

    @Query("SELECT * FROM transactions")
    fun getAll(): LiveData<List<Transaction>>

    @Query("DELETE FROM transactions")
    fun deleteAll()
}