package br.com.stone.vianna.starstore.entity

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: Item): Completable

    @Update
    fun updateItem(item: Item): Completable

    @Delete
    fun deleteItem(item: Item): Completable

    @Query("SELECT * FROM Item WHERE title == :title")
    fun getItemByName(title: String): Maybe<List<Item>>

    @Query("SELECT * FROM Item")
    fun getItems(): Maybe<List<Item>>

    @Query("SELECT COUNT(*) FROM Item")
    fun getItemsCount(): Maybe<Int>

    @Query("DELETE FROM Item")
    fun removeItems(): Completable
}