package br.com.stone.vianna.starstore.view.itemList

import android.util.Log
import br.com.stone.vianna.starstore.entity.Item
import br.com.stone.vianna.starstore.entity.ItemDao
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ItemListPresenter(private val view: ItemListContract.View,
                        private val itemListRepository: ItemListRepository,
                        private val itemDao: ItemDao)
    : ItemListContract.Presenter {

    override fun init() {
        view.displayLoading()
        getTotalOfItems{ view.setupBadge(it) }
        getRemoteItems()
    }

    private fun getRemoteItems() {
        itemListRepository.getItems(
                { onSuccessLoadItems(it) },
                { onErrorLoadItems(it) })
    }

    private fun onSuccessLoadItems(items: List<Item>) {
        view.hideLoading()
        view.updateListItems(items)
    }

    private fun onErrorLoadItems(error: String) {
        view.hideLoading()
        Log.e("ERRO GETTING ITEMS", error)
    }

    override fun onItemClicked(item: Item) {
        Completable
                .fromAction { itemDao.insertItem(item) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { getTotalOfItems { view.setupBadge(it) } },
                        { Log.d("RxJava", "Insert Error") }
                )
    }

    private fun getTotalOfItems(onComplete: (total: Int) -> Unit) {
        itemDao.getItemsCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { total: Int -> onComplete.invoke(total) }
    }

    override fun onCartIconClicked() {
        getTotalOfItems { if (it > 0) view.openShoppingCart() }
    }

    override fun onHistoryIconClicked() {
        view.openHistory()
    }

}
