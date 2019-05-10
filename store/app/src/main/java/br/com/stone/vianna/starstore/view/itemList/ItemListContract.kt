package br.com.stone.vianna.starstore.view.itemList

import br.com.stone.vianna.starstore.entity.Item

class ItemListContract {

    interface View {
        fun displayLoading()
        fun hideLoading()
        fun updateListItems(items: List<Item>)
    }

    interface Presenter {
        fun init()
    }

}