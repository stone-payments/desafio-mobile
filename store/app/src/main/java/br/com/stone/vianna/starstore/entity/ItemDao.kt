package br.com.stone.cryptowallet.entity

import android.arch.persistence.room.*
import br.com.stone.cryptowallet.entity.Item
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: Item)

    @Update
    fun updateItem(item: Item)

    @Delete
    fun deleteItem(item: Item)

    @Query("SELECT * FROM Item WHERE title == :title")
    fun getItemByName(title: String): Single<List<Item>>

    @Query("SELECT * FROM Item")
    fun getItems(): Maybe<List<Item>>

    @Query("SELECT COUNT(*) FROM Item")
    fun getItemsCount(): Maybe<Int>

    @Query("DELETE FROM Item")
    fun removeItems()
}