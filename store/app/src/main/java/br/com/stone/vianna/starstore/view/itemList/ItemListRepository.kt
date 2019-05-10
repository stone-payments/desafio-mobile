package br.com.stone.vianna.starstore.view.itemList

import br.com.stone.vianna.starstore.entity.Item
import br.com.stone.vianna.starstore.extensions.addThreads
import br.com.stone.vianna.starstore.extensions.parser

interface ItemListRepository {

    fun getItems(onSuccess: ((List<Item>) -> Unit)? = null,
                 onError: ((error: String) -> Unit)? = null)
}

class ItemListRepositoryImpl(private val itemListDataSource: ItemListDataSource)
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

}
