package com.example.pharol.temminstore.item

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.pharol.temminstore.shoppingcart.ShoppingCart
import com.example.pharol.temminstore.shoppingcart.ShoppingCartRepository
import java.util.concurrent.Executors


class ItemViewModel(val itemRepository: ItemRepository,
                    val cartRepository: ShoppingCartRepository): ViewModel() {

    val listItemsCart : MutableMap<Item,Int> = mutableMapOf()
    val liveItemsCart = MutableLiveData<Map<Item,Int>>()
    var item: Item? = null

    fun init(cart: List<ShoppingCart>){
            listItemsCart.clear()
            cart.forEach {
                listItemsCart[it.item]=it.qtdItems
            }
            liveItemsCart.value = listItemsCart
    }


    fun getItemList() : LiveData<List<Item>> = itemRepository.getItemList()

    fun getItem(id:Int) : LiveData<Item> = itemRepository.getItem(id)

    fun addItemToCart(item : Item, value: Int = 1){
            val qtd = listItemsCart[item] ?: 0
            listItemsCart[item] = qtd + value
            liveItemsCart.value = listItemsCart
    }

    fun saveCart(): Boolean {
        if (listItemsCart.isNotEmpty()){
            Executors.newSingleThreadExecutor().execute({
                val id = cartRepository.getIndex()
                listItemsCart.forEach {
                    cartRepository.save(ShoppingCart(id,it.key,it.value))
                }
                listItemsCart.clear()
            })
            return true
        } else{
            return false
        }
    }

    fun getCartItems(): LiveData<Map<Item, Int>> = liveItemsCart

    /**
     * Return a List containing Cart Items
     */
    fun returnOngoingCart(): LiveData<List<ShoppingCart>> = cartRepository.getCart()
}