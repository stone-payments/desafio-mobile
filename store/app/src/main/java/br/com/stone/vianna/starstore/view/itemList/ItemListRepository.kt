package br.com.stone.vianna.starstore.view.itemList

import br.com.stone.vianna.starstore.entity.Item
import br.com.stone.vianna.starstore.entity.ItemDao
import br.com.stone.vianna.starstore.extensions.addThreads
import br.com.stone.vianna.starstore.extensions.parser
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface ItemListRepository {

    fun getItems(onSuccess: ((List<Item>) -> Unit)? = null,
                 onError: ((error: String) -> Unit)? = null)

    fun saveItemLocally(item: Item, onComplete: () -> Unit)
    fun getTotalOfItems(onComplete: (total: Int) -> Unit)
    fun removeItems(onComplete: () -> Unit)
}

class ItemListRepositoryImpl(private val itemListDataSource: ItemListDataSource,
                             private val itemDao: ItemDao)
    : ItemListRepository {


    override fun getItems(onSuccess: ((List<Item>) -> Unit)?,
                          onError: ((error: String) -> Unit)?) {

        itemListDataSource
                .getItems()
                .addThreads()
                .subscribe({
                    onSuccess?.invoke(it)
                }, {
                    onError?.invoke(it.parser.error)
                })
    }

    override fun saveItemLocally(item: Item, onComplete: () -> Unit) {
        Completable
                .fromAction { itemDao.insertItem(item) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { onComplete.invoke() },
                        { })
    }

    override fun getTotalOfItems(onComplete: (total: Int) -> Unit) {
        itemDao.getItemsCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { total: Int -> onComplete.invoke(total) }
    }

    override fun removeItems(onComplete: () -> Unit) {
        Completable
                .fromAction { itemDao.removeItems() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { onComplete.invoke() },
                        { })
    }
}
