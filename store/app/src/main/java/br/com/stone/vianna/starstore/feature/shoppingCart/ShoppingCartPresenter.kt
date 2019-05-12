package br.com.stone.vianna.starstore.feature.shoppingCart

import br.com.stone.vianna.starstore.entity.Item
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class ShoppingCartPresenter(private val view: ShoppingCartContract.View,
                            shoppingCartRepository: ShoppingCartRepository)

    : ShoppingCartContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    private val cartItemsStream = shoppingCartRepository.getCartItems().share()

    override fun init() {

        cartItemsStream
                .subscribe { updateViewWithCartItems(it) }
                .addTo(compositeDisposable)
    }

    private fun updateViewWithCartItems(cartItems: List<Item>?) {
        cartItems?.let {
            view.updateCartItems(it)
            val totalValue = getTotalValue(it)
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
        cartItemsStream
                .subscribe {
                    if (it.isNotEmpty()) {
                        val totalValue = getTotalValue(it)
                        view.openCheckout(totalValue)
                    }
                }
                .addTo(compositeDisposable)
    }

    override fun removeItem(item: Item) {
        cartItemsStream
                .subscribe {
                    updateViewWithCartItems(it)
                }
                .addTo(compositeDisposable)
    }
}