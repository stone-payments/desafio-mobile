package br.com.stone.vianna.starstore.view.itemList

import android.util.Log
import br.com.stone.vianna.starstore.entity.Item
import br.com.stone.vianna.starstore.entity.ItemDao

class ItemListPresenter(private val view: ItemListContract.View,
                        private val itemListRepository: ItemListRepository,
                        private val itemDao: ItemDao)
    : ItemListContract.Presenter {

    override fun init() {
        view.displayLoading()
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

}
