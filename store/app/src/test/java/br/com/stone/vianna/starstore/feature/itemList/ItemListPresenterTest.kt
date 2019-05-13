package br.com.stone.vianna.starstore.feature.itemList

import br.com.stone.vianna.starstore.entity.Item
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.subjects.BehaviorSubject
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.times

@RunWith(JUnitParamsRunner::class)
class ItemListPresenterTest {

    private val itemsSubject = BehaviorSubject.createDefault(items)
    private val totalItemsSubject = BehaviorSubject.create<Int>()
    private val itemListRepository: ItemListRepository = mock {
        on { getTotalOfItems() }.thenReturn(totalItemsSubject)
        on { getItems() }.thenReturn(itemsSubject)
    }

    private val view: ItemListContract.View = mock()
    private val presenter = ItemListPresenter(view, itemListRepository)

    @Test
    fun `show loading when init() is called`() {
        presenter.init()
        verify(view).displayLoading()
    }

    @Test
    fun `fetch items from repository when init() is called`() {
        presenter.init()
        verify(itemListRepository).getTotalOfItems()
    }

    @Test
    fun `hide loading and show items when repository returns valid data`() {
        presenter.init()

        verify(view).hideLoading()
        verify(view).updateListItems(items)
    }

    @Test
    fun `hide loading and show error when repository returns error`() {
        itemsSubject.onError(Throwable("Error"))

        presenter.init()

        verify(view).hideLoading()
        verify(view).showError("Error")
    }

    @Test
    @Parameters(
            "1", "10", "100", "1000"
    )
    fun `update badge counter`(itemTotal: Int) {
        presenter.updateBadge()

        totalItemsSubject.onNext(itemTotal)
        verify(view).setupBadge(itemTotal)
    }

    @Test
    @Parameters(
            "0"
    )
    fun `when cart is empty it should not open CartActivity`(itemTotal: Int) {
        presenter.onCartIconClicked()

        totalItemsSubject.onNext(itemTotal)
        verify(view, times(0)).openShoppingCart()

    }

    @Test
    fun `when no items is fetched from server it should display an empty view`() {
        itemsSubject.onNext(listOf())

        presenter.init()

        verify(view).hideLoading()
        verify(view).displayEmptyView()
    }


    private companion object {
        var items = listOf(
                Item(id = 1, title = "title 1", price = 200, seller = "Seller1", thumbnailHd = ""),
                Item(id = 2, title = "title 2", price = 100, seller = "Seller2", thumbnailHd = ""),
                Item(id = 3, title = "title 3", price = 250, seller = "Seller3", thumbnailHd = "")
        )
    }
}
