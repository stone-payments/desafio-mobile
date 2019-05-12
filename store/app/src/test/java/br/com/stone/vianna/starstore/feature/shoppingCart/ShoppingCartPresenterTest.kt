package br.com.stone.vianna.starstore.feature.shoppingCart

import br.com.stone.vianna.starstore.entity.Item
import br.com.stone.vianna.starstore.extensions.toMoneyFormat
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Completable
import io.reactivex.subjects.BehaviorSubject
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(JUnitParamsRunner::class)
class ShoppingCartPresenterTest {

    private val itemsSubject = BehaviorSubject.createDefault(ShoppingCartPresenterTest.cartItems)
    private val valueItemsSubject = BehaviorSubject.create<Int>()
    private val shoppingCartRepository: ShoppingCartRepository = mock {
        on { getCartItems() }.thenReturn(itemsSubject)
        on { removeItem(any()) }.thenReturn(Completable.complete())
    }

    private val view: ShoppingCartContract.View = mock()
    private val presenter = ShoppingCartPresenter(view, shoppingCartRepository)

    @Test
    fun `fetch cart items from repository when init() is called`() {
        presenter.init()
        verify(shoppingCartRepository).getCartItems()
    }

    @Test
    @Parameters("550")
    fun `update value text when init() is called`(totalValue: Int) {
        presenter.init()
        valueItemsSubject.onNext(totalValue)
        verify(view).setTotalValue(totalValue.toMoneyFormat())
    }


    @Test
    @Parameters("550")
    fun `update view when removeItem() is called`(totalValue: Int) {
        val item = Item(id = 1, title = "title 1", price = 200, seller = "Seller1",
                thumbnailHd = "")

        presenter.removeItem(item)
        valueItemsSubject.onNext(totalValue)

        verify(view).updateCartItems(cartItems)
        verify(view).setTotalValue(totalValue.toMoneyFormat())
    }

    private companion object {
        val cartItems = listOf(
                Item(id = 1, title = "title 1", price = 200, seller = "Seller1", thumbnailHd = ""),
                Item(id = 2, title = "title 2", price = 100, seller = "Seller2", thumbnailHd = ""),
                Item(id = 3, title = "title 3", price = 250, seller = "Seller3", thumbnailHd = "")
        )
    }
}