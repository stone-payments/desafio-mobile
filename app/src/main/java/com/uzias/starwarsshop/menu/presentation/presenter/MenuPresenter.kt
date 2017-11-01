package com.uzias.starwarsshop.menu.presentation.presenter

import com.uzias.starwarsshop.core.presentation.BasePresenter
import com.uzias.starwarsshop.menu.presentation.navigation.MenuNavigation
import com.uzias.starwarsshop.menu.presentation.view.MenuView

interface MenuPresenter: BasePresenter {

    fun clickedMenuProducts()
    fun resultTransactionOk()
    fun clickedMenuTransactions()

}

class MenuPresenterImpl(private val navigation: MenuNavigation, private val view: MenuView) :
        MenuPresenter {

    init {
        clickedMenuProducts()
    }

    override fun clickedMenuProducts() {
        navigation.goToProducts()
        view.closeDrawerMenu()
        view.setTitleProducts()
    }

    override fun clickedMenuTransactions() {
        navigation.goToTransactions()
        view.closeDrawerMenu()
        view.setTitleTransactions()
    }

    override fun resultTransactionOk() {
        clickedMenuTransactions()
    }

}
