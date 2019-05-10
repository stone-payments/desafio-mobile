package br.com.stone.vianna.starstore.view.shoppingCart

import br.com.stone.vianna.starstore.entity.Item

class ShoppingCartPresenter(private val view: ShoppingCartContract.View,
                            private val shoppingCartRepository: ShoppingCartRepository)
    : ShoppingCartContract.Presenter {

    override fun init() {
        shoppingCartRepository.getCartItems { updateViewWithCartItems(it) }
    }

    private fun updateViewWithCartItems(cartItems: List<Item>?) {
        if (cartItems != null) {
            view.updateCartItems(cartItems)
            val totalValue = getTotalValue(cartItems)
            view.setTotalValue(totalValue)
        }
    }

    private fun getTotalValue(cartItems: List<Item>?): Int {
        var totalValue = 0

        cartItems?.forEach {
            totalValue += it.price
        }
        return totalValue
    }

    override fun onProceedToCheckoutButtonClicked() {
        shoppingCartRepository.getCartItems {
            it?.let {
                if (it.isNotEmpty()) {
                    val totalValue = getTotalValue(it)
                    view.openCheckout(totalValue)
                }
            }
        }
    }

    override fun removeItem(item: Item) {
        shoppingCartRepository.removeItem(item) {
            shoppingCartRepository.getCartItems { updateViewWithCartItems(it) }
        }
    }
}