package br.com.ygorcesar.desafiostone.data

import br.com.ygorcesar.desafiostone.model.Item

class ShoppingCart private constructor() {
    init {
    }

    private object ourInstance {
        val INSTANCE = ShoppingCart()
    }

    companion object {
        val instance: ShoppingCart by lazy { ourInstance.INSTANCE }
    }

    val items: MutableList<Item> = mutableListOf()

    fun addItem(item: Item?) = item?.let { items.add(item) }

    fun cartPrice(): Double {
        var price = 0.0
        items.forEach { price += it.price }
        return price
    }

    fun clear() = items.clear()
}
