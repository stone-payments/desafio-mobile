package com.stone.desafiomobile.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.stone.desafiomobile.model.PurchaseLog

@Dao
interface PurchaseLogDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg data: PurchaseLog): LongArray

    @Query("SELECT * FROM purchase_logs")
    fun listAll(): LiveData<List<PurchaseLog>>

    @Query("SELECT * FROM purchase_logs where id=:arg0")
    fun findById(id: Long): LiveData<PurchaseLog>
}