package com.example.pharol.temminstore.item

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.pharol.temminstore.item.Item
import com.example.pharol.temminstore.util.BigDecimalConverter


@Dao
@TypeConverters(BigDecimalConverter::class)
interface ItemDAO {

    @Query("SELECT * FROM item")
    fun getAll(): LiveData<List<Item>>

    @Query("SELECT * FROM item where id = :p0")
    fun getItem(id: Int): LiveData<Item>

    @Query("SELECT COUNT(*) FROM item")
    fun hasItems(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(list: List<Item>)

    @Query("SELECT id FROM item where title = :p0")
    fun getItemID(itemTitle: String): Int

    @Query("DELETE FROM item WHERE id NOT IN (:p0)")
    fun refreshBase(list: List<Int>)
}