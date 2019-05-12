package br.com.stone.vianna.starstore.feature.shoppingCart

import br.com.stone.vianna.starstore.entity.Item
import br.com.stone.vianna.starstore.extensions.toMoneyFormat
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class ShoppingCartPresenter(private val view: ShoppingCartContract.View,
                            private val shoppingCartRepository: ShoppingCartRepository)

    : ShoppingCartContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    private val cartItemsStream = shoppingCartRepository.getCartItems().share()

    override fun init() {
        updateViewWithCartItems()
    }

    private fun updateViewWithCartItems() {
        cartItemsStream
                .subscribe {
                    view.updateCartItems(it)
                    val totalValue = getTotalValue(it)
                    view.setTotalValue(totalValue.toMoneyFormat())
                }
                .addTo(compositeDisposable)
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

        shoppingCartRepository.removeItem(item)
                .subscribe {
                    updateViewWithCartItems()
                }
                .addTo(compositeDisposable)
    }

    override fun clearEvents() {
        compositeDisposable.clear()
    }
}