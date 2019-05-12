package br.com.stone.vianna.starstore.feature.itemList

import br.com.stone.vianna.starstore.entity.Item
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class ItemListPresenter(private val view: ItemListContract.View,
                        private val itemListRepository: ItemListRepository)
    : ItemListContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private val totalItemsStream = itemListRepository.getTotalOfItems().share()

    override fun init() {
        view.displayLoading()

        itemListRepository.getItems()
                .subscribe({
                    onSuccessLoadItems(it)
                }, {
                    onErrorLoadItems(it.message.toString())
                }).addTo(compositeDisposable)
    }

    override fun updateBadge() {
        totalItemsStream
                .subscribe { view.setupBadge(it) }
                .addTo(compositeDisposable)
    }

    override fun onItemClicked(item: Item) {
        itemListRepository.saveItemLocally(item)
                .subscribe { updateBadge() }
                .addTo(compositeDisposable)
    }

    override fun onCartIconClicked() {
        totalItemsStream
                .subscribe { total: Int -> if (total > 0) view.openShoppingCart() }
                .addTo(compositeDisposable)
    }

    override fun onHistoryIconClicked() {
        view.openHistory()
    }

    override fun clearEvents() {
        compositeDisposable.clear()
    }

    private fun onSuccessLoadItems(items: List<Item>) {
        view.hideLoading()
        view.updateListItems(items)
    }

    private fun onErrorLoadItems(error: String) {
        view.hideLoading()
        view.showError()
    }
}
