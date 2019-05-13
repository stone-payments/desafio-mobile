package br.com.stone.vianna.starstore.feature.shoppingCart

import br.com.stone.vianna.starstore.entity.Item

class ShoppingCartContract {

    interface View {
        fun updateCartItems(items: List<Item>)
        fun setTotalValue(totalValue: String)
        fun openCheckout(value: Int)
    }

    interface Presenter {
        fun init()
        fun removeItem(item: Item)
        fun onProceedToCheckoutButtonClicked()
        fun clearEvents()
    }
}