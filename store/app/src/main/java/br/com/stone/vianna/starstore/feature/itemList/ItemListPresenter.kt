package br.com.stone.vianna.starstore.feature.itemList

import android.util.Log
import br.com.stone.vianna.starstore.entity.Item
import br.com.stone.vianna.starstore.helper.addThreads
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ItemListPresenter(private val view: ItemListContract.View,
                        private val itemListRepository: ItemListRepository)
    : ItemListContract.Presenter {

    private var compositeDisposable = CompositeDisposable()

    override fun init() {
        view.displayLoading()
        getRemoteItems()
    }

    override fun updateBadge() {
        var updateBadgeDisposable = itemListRepository.getTotalOfItems()
                .doOnSuccess { total: Int -> view.setupBadge(total) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()

        compositeDisposable.add(updateBadgeDisposable)
    }

    private fun getRemoteItems() {

        var getItemsDisposable = itemListRepository.getItems()
                .addThreads()
                .subscribe({
                    onSuccessLoadItems(it)
                }, {
                    onErrorLoadItems(it.localizedMessage)
                })

        compositeDisposable.add(getItemsDisposable)
    }

    private fun onSuccessLoadItems(items: List<Item>) {
        view.hideLoading()
        view.updateListItems(items)
    }

    private fun onErrorLoadItems(error: String) {
        view.hideLoading()
    }

    override fun onItemClicked(item: Item) {
        val itemClickedDisposable = Observable
                .fromCallable { itemListRepository.saveItemLocally(item) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { Log.d("teste", "Testes") }
                .subscribe { updateBadge() }

        compositeDisposable.add(itemClickedDisposable)
    }

    override fun onCartIconClicked() {
        var totalItemsDisposable = itemListRepository.getTotalOfItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { total: Int -> if (total > 0) view.openShoppingCart() }

        compositeDisposable.add(totalItemsDisposable)
    }

    override fun onHistoryIconClicked() {
        view.openHistory()
    }

    override fun clearEvents() {
        compositeDisposable.clear()
    }
}
