package br.com.stone.vianna.starstore.entity

import androidx.room.*
import io.reactivex.Maybe

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: Item)

    @Update
    fun updateItem(item: Item)

    @Delete
    fun deleteItem(item: Item)

    @Query("SELECT * FROM Item WHERE title == :title")
    fun getItemByName(title: String): Maybe<List<Item>>

    @Query("SELECT * FROM Item")
    fun getItems(): Maybe<List<Item>>

    @Query("SELECT COUNT(*) FROM Item")
    fun getItemsCount(): Maybe<Int>

    @Query("DELETE FROM Item")
    fun removeItems()
}