package br.com.stone.vianna.starstore.view.shoppingCart

import br.com.stone.vianna.starstore.entity.Item
import br.com.stone.vianna.starstore.entity.ItemDao
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ShoppingCartPresenter(private val view: ShoppingCartContract.View,
                            private val itemDao: ItemDao) : ShoppingCartContract.Presenter {


    override fun removeItem(item: Item) {
        Completable
                .fromAction { itemDao.deleteItem(item) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    getCartItems { updateViewWithCartItems(it) }
                }

    }

    override fun init() {
        getCartItems { updateViewWithCartItems(it) }
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

    private fun getCartItems(onComplete: (List<Item>?) -> Unit) {
        itemDao.getItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { t: List<Item>? -> onComplete.invoke(t) }
    }

    override fun onProceedToCheckoutButtonClicked() {
        getCartItems {
            it?.let {
                if (it.isNotEmpty()) {
                    val totalValue = getTotalValue(it)
                    view.openCheckout(totalValue)
                }
            }
        }
    }
}