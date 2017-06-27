package com.example.pharol.temminstore.shoppingcart

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import com.example.pharol.temminstore.item.Item


@Entity(primaryKeys = arrayOf("id","it_id"))
data class ShoppingCart(val id: Int, @Embedded(prefix = "it_") var item: Item, var qtdItems: Int = 0)