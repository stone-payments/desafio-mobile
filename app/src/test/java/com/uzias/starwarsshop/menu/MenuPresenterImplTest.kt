package com.uzias.starwarsshop.menu.presentation.presenter

import com.nhaarman.mockito_kotlin.*
import com.uzias.starwarsshop.menu.presentation.navigation.MenuNavigation
import com.uzias.starwarsshop.menu.presentation.view.MenuView
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MenuPresenterImplTest {

    private val navigation: MenuNavigation = mock()
    private val view: MenuView = mock()

    @InjectMocks
    private lateinit var presenter: MenuPresenterImpl

    @Test
    fun must_call_in_init_presenter(){
        verify(view).closeDrawerMenu()
        verify(view).setTitleProducts()
        verify(navigation).goToProducts()
    }

    @Test
    fun must_call_when_click_menu_products(){
        presenter.clickedMenuProducts()
        verify(view, times(2)).closeDrawerMenu()
        verify(view, times(2)).setTitleProducts()
        verify(navigation, times(2)).goToProducts()
    }

    @Test
    fun must_call_when_click_menu_transactions(){
        presenter.clickedMenuTransactions()
        verify(view, times(2)).closeDrawerMenu()
        verify(view).setTitleTransactions()
        verify(navigation).goToTransactions()
    }

    @Test
    fun must_call_when_result_transaction_ok(){
        presenter.resultTransactionOk()
        verify(view, times(2)).closeDrawerMenu()
        verify(view).setTitleTransactions()
        verify(navigation).goToTransactions()
    }

}