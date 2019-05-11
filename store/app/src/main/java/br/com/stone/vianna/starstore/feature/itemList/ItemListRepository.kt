package br.com.stone.vianna.starstore.feature.itemList

import br.com.stone.vianna.starstore.entity.Item
import br.com.stone.vianna.starstore.entity.ItemDao
import io.reactivex.Maybe
import io.reactivex.Observable

interface ItemListRepository {

    fun getItems(): Observable<List<Item>>
    fun getTotalOfItems(): Maybe<Int>
    fun saveItemLocally(item: Item)
    fun removeItems()
}

class ItemListRepositoryImpl(private val itemListDataSource: ItemListDataSource,
                             private val itemDao: ItemDao)
    : ItemListRepository {


    override fun getItems(): Observable<List<Item>> {

       return itemListDataSource.getItems()

    }

    override fun saveItemLocally(item: Item) {
        itemDao.insertItem(item)

    }

    override fun getTotalOfItems(): Maybe<Int> {
        return itemDao.getItemsCount()
    }

    override fun removeItems() {
        itemDao.removeItems()
    }
}
