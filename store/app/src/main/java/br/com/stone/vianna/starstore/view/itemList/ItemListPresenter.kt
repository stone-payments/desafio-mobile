package br.com.stone.vianna.starstore.view.itemList

import br.com.stone.vianna.starstore.entity.Item

class ItemListPresenter(private val view: ItemListContract.View,
                        private val itemListRepository: ItemListRepository)
    : ItemListContract.Presenter {

    override fun init() {
        view.displayLoading()
        itemListRepository.getTotalOfItems { view.setupBadge(it) }
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
    }

    override fun onItemClicked(item: Item) {
        itemListRepository.saveItemLocally(item) {
            itemListRepository.getTotalOfItems { view.setupBadge(it) }
        }
    }

    override fun onCartIconClicked() {
        itemListRepository.getTotalOfItems { if (it > 0) view.openShoppingCart() }
    }

    override fun onHistoryIconClicked() {
        view.openHistory()
    }

}
