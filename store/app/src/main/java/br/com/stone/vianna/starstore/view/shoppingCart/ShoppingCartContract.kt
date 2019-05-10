package br.com.stone.vianna.starstore.view.shoppingCart

import br.com.stone.vianna.starstore.entity.Item

class ShoppingCartContract {

    interface View {
        fun updateCartItems(items: List<Item>)
        fun setTotalValue(totalValue: Int)
        fun openCheckout(value: Int)
    }

    interface Presenter {
        fun init()
        fun removeItem(item: Item)
        fun onProceedToCheckoutButtonClicked()
    }
}