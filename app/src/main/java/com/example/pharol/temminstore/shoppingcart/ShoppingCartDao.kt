package com.example.pharol.temminstore.shoppingcart

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import java.math.BigDecimal


@Dao
interface ShoppingCartDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(shoppingDao: ShoppingCart)

    @Query("SELECT * FROM shoppingcart where id = :p0")
    fun loadCart(id: Int) : LiveData<List<ShoppingCart>>

    @Query("SELECT * FROM shoppingcart where id = (SELECT max(id) FROM shoppingCart)")
    fun loadLastCart() : LiveData<List<ShoppingCart>>


    @Query("SELECT sum(it_price * qtdItems) from shoppingcart where id = (SELECT max(id) FROM shoppingCart)")
    fun cartTotal(): LiveData<BigDecimal>

    @Query("SELECT max(id) FROM shoppingcart")
    fun loadLast(): Int

    @Query("SELECT COUNT(*) FROM shoppingcart")
    fun hasCart(): Int

    @Delete
    fun deleteItem(shoppingCart: ShoppingCart)

    @Query("DELETE FROM shoppingcart where id = (SELECT max(id) FROM shoppingCart)")
    fun delete(): Unit
}