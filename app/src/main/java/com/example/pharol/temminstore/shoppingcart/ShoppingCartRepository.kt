package com.example.pharol.temminstore.shoppingcart

import android.arch.lifecycle.LiveData
import java.math.BigDecimal
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingCartRepository
@Inject constructor(val shoppingCartDao: ShoppingCartDao){

    fun save(shoppingCart: ShoppingCart){
        Executors.newSingleThreadExecutor().execute({
            shoppingCartDao.save(shoppingCart)
        })
    }

    fun delete(){
        Executors.newSingleThreadExecutor().execute({
            shoppingCartDao.delete()
        })
    }

    fun deleteItem(shoppingCart: ShoppingCart){
        Executors.newSingleThreadExecutor().execute({
            shoppingCartDao.deleteItem(shoppingCart)
        })
    }

    fun getCart(id: Int = -1): LiveData<List<ShoppingCart>>{
        if (id == -1){
            return shoppingCartDao.loadLastCart()
        } else {
            return shoppingCartDao.loadCart(id)
        }
    }

    fun getIndex(): Int = shoppingCartDao.loadLast()

    fun getCartTotal(): LiveData<BigDecimal> = shoppingCartDao.cartTotal()

}