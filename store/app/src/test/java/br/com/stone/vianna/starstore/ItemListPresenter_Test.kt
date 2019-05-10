package br.com.stone.vianna.starstore

import br.com.stone.vianna.starstore.feature.itemList.ItemListContract
import br.com.stone.vianna.starstore.feature.itemList.ItemListPresenter
import br.com.stone.vianna.starstore.feature.itemList.ItemListRepository
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.internal.verification.Times

class ItemListPresenter_Test : KoinTest {

    lateinit var presenter: ItemListContract.Presenter

    @Mock
    lateinit var itemListRepository: ItemListRepository

    @Mock
    lateinit var view: ItemListContract.View

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = ItemListPresenter(view, itemListRepository)

    }

    @Test
    fun whenCartIsSelected_itShouldFetchItemsFromRepository() {
        presenter.init()
        verify(itemListRepository, Times(1)).getTotalOfItems({})
    }
    
    @After
    fun finish() {
        stopKoin()
    }


}
