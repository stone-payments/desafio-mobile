package personal.pedrofigueiredo.milleniumstore.data

class ShoppingCart {
    private val items = mutableMapOf<Product, Int>()

    fun addToCart(product: Product, quantity: Int) {
        items.put(product, quantity)
    }

    fun getTotalPrice(): Int {
        return items.entries.sumBy { it.key.price * it.value }
    }

    fun getQuantityByProduct(key: Product): Int? {
        return when (items.containsKey(key)) {
            true -> items[key]
            false -> 0
        }
    }

    fun getCartSize(): Int {
        return items.size
    }

    override fun toString(): String {
        var str: String = "["
        for ((key, value) in items.entries) {
            str += "Product:$key, Quantity:$value "
        }
        str += "]"

        return str
    }
}