package br.com.stone.vianna.starstore.feature.itemList

import br.com.stone.vianna.starstore.RxTestRule
import br.com.stone.vianna.starstore.entity.Item
import br.com.stone.vianna.starstore.entity.ItemDao
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Maybe
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.Spy

class ItemListRepositoryTest {

    @Rule
    @JvmField
    val rule = RxTestRule()

    private val itemListDataSource: ItemListDataSource = mock {
        on { getItems() }.thenReturn(Observable.just(items))
    }

    private val itemDao: ItemDao = mock {
        on { getItems() }.thenReturn(Maybe.just(items))
        on { getItemsCount() }.thenReturn(Maybe.just(3))
    }

    @Spy
    private val repository = ItemListRepositoryImpl(itemListDataSource, itemDao)

    @Test
    fun `call dao when removeItems() is called`() {
        repository.removeItems().test()
        verify(itemDao).removeItems()
    }

    @Test
    fun `call data source when getItems() is called`() {
        repository.getItems().test()

        verify(itemListDataSource).getItems()
    }

    @Test
    fun `call dao when getTotalOfItems() is called`() {
        repository.getTotalOfItems().test()

        verify(itemDao).getItemsCount()
    }

    @Test
    fun `call dao when saveItemLocally() is called`() {
        val item = Item(id = 1, title = "title 1", price = 200, seller = "Seller1", thumbnailHd = "")

        repository.saveItemLocally(item).test()

        verify(itemDao).insertItem(item)
    }

}

val items = listOf(
        Item(id = 1, title = "title 1", price = 200, seller = "Seller1", thumbnailHd = ""),
        Item(id = 2, title = "title 2", price = 100, seller = "Seller2", thumbnailHd = ""),
        Item(id = 3, title = "title 3", price = 250, seller = "Seller3", thumbnailHd = "")
)