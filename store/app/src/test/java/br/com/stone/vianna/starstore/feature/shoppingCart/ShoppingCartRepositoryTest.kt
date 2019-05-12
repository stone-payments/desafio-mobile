package br.com.stone.vianna.starstore.feature.shoppingCart

import br.com.stone.vianna.starstore.RxTestRule
import br.com.stone.vianna.starstore.entity.Item
import br.com.stone.vianna.starstore.entity.ItemDao
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Completable
import io.reactivex.Maybe
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Spy

class ShoppingCartRepositoryTest {

    @Rule
    @JvmField
    val rule = RxTestRule()

    private val itemDao: ItemDao = mock {
        on { getItems() }.thenReturn(Maybe.just(items))
        on { deleteItem(any()) }.thenReturn(Completable.complete())
    }

    @Spy
    private val repository = ShoppingCartRepositoryImpl(itemDao)

    @Test
    fun `call dao when deleteItem() is called`() {

        val item = Item(id = 1, title = "title 1", price = 200, seller = "Seller1",
                thumbnailHd = "")

        repository.removeItem(item).test()
        Mockito.verify(itemDao).deleteItem(item)
    }

    @Test
    fun `call dao when getCartItems() is called`() {
        repository.getCartItems().test()

        Mockito.verify(itemDao).getItems()
    }

}

val items = listOf(
        Item(id = 1, title = "title 1", price = 200, seller = "Seller1", thumbnailHd = ""),
        Item(id = 2, title = "title 2", price = 100, seller = "Seller2", thumbnailHd = ""),
        Item(id = 3, title = "title 3", price = 250, seller = "Seller3", thumbnailHd = "")
)