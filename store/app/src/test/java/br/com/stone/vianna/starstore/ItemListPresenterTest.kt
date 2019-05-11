package br.com.stone.vianna.starstore

import br.com.stone.vianna.starstore.feature.itemList.ItemListContract
import br.com.stone.vianna.starstore.feature.itemList.ItemListPresenter
import br.com.stone.vianna.starstore.feature.itemList.ItemListRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin

class ItemListPresenterTest {

    lateinit var presenter: ItemListContract.Presenter

    @MockK
    lateinit var itemListRepository: ItemListRepository

    @MockK
    lateinit var view: ItemListContract.View

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        presenter = spyk(ItemListPresenter(view, itemListRepository))

    }

    @Test
    fun whenCartIsSelected_itShouldDisplayLoading() {
        presenter.init()
        verify { view.displayLoading() }

    }

    @Test
    fun whenCartIsSelected_itShouldFetchItemsFromRepository() {

    }

    @Test
    fun whenItemsListIsFetched_itShouldSetThemOnView() {

    }

    @Test
    fun whenCartIconIsClicked_itShouldCheckNumberOfItems() {
        presenter.onCartIconClicked()
        verify { itemListRepository.getTotalOfItems() }
    }

    @Test
    fun whenHistoryIconIsClicked_itShouldCallViewNavigation() {
//        presenter.onCartIconClicked()
//        verify(view).openShoppingCart()
    }

    @Test
    fun whenPresenterIsInitialized_ItShouldRequestItems() {
//        every { itemListRepository.getItems() } answers {
//            just(mutableListOf(
//                    Item(),
//                    Item()))
//        }
//
//        val testObserver = itemListRepository.getItems().test()
//        testObserver.awaitTerminalEvent()
//
//        testObserver
//                .assertNoErrors()
//                .assertValue { l -> l.size == 2 }


        presenter.init()
        verify { itemListRepository.getItems() }


    }

    @After
    fun finish() {
        stopKoin()
    }


}
