package br.com.stone.vianna.starstore.feature.itemList

import br.com.stone.vianna.starstore.entity.Item

class ItemListContract {

    interface View {
        fun displayLoading()
        fun hideLoading()
        fun updateListItems(items: List<Item>)
        fun setupBadge(number: Int)
        fun openShoppingCart()
        fun openHistory()
    }

    interface Presenter {
        fun init()
        fun onCartIconClicked()
        fun onItemClicked(item: Item)
        fun onHistoryIconClicked()
        fun updateBadge()
    }

}