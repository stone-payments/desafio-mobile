package br.com.stone.vianna.starstore.feature.itemList

import br.com.stone.vianna.starstore.entity.Item
import br.com.stone.vianna.starstore.entity.ItemDao
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface ItemListRepository {

    fun getItems(): Observable<List<Item>>
    fun getTotalOfItems(): Observable<Int>
    fun saveItemLocally(item: Item): Completable
    fun removeItems()
}

class ItemListRepositoryImpl(private val itemListDataSource: ItemListDataSource,
                             private val itemDao: ItemDao)
    : ItemListRepository {


    override fun getItems(): Observable<List<Item>> {
        return itemListDataSource.getItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun saveItemLocally(item: Item): Completable {
        return Completable.fromAction{ itemDao.insertItem(item)}
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getTotalOfItems(): Observable<Int> {
        return itemDao.getItemsCount().toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun removeItems() {
        itemDao.removeItems()
    }
}
