package br.com.stone.vianna.starstore.feature.shoppingCart

import br.com.stone.vianna.starstore.entity.Item
import br.com.stone.vianna.starstore.entity.ItemDao
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface ShoppingCartRepository {

    fun getCartItems(onComplete: (List<Item>?) -> Unit)
    fun removeItem(item: Item, onComplete: () -> Unit)
}

class ShoppingCartRepositoryImpl(private val itemDao: ItemDao)
    : ShoppingCartRepository {

    override fun getCartItems(onComplete: (List<Item>?) -> Unit) {
        itemDao.getItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { t: List<Item>? -> onComplete.invoke(t) }
    }

    override fun removeItem(item: Item, onComplete: () -> Unit) {
        Completable
                .fromAction { itemDao.deleteItem(item) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    getCartItems { onComplete.invoke() }
                }

    }

}