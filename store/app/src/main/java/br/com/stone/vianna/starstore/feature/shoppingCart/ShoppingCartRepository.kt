package br.com.stone.vianna.starstore.feature.shoppingCart

import br.com.stone.vianna.starstore.entity.Item
import br.com.stone.vianna.starstore.entity.ItemDao
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface ShoppingCartRepository {

    fun getCartItems(): Observable<List<Item>>
    fun removeItem(item: Item): Completable
}

class ShoppingCartRepositoryImpl(private val itemDao: ItemDao)
    : ShoppingCartRepository {

    override fun getCartItems(): Observable<List<Item>> {
        return itemDao.getItems()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun removeItem(item: Item): Completable {
        return Completable
                .fromAction { itemDao.deleteItem(item) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}