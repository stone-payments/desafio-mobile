package com.uzias.starwarsshop.menu.presentation.navigation

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.uzias.starwarsshop.R
import com.uzias.starwarsshop.products.presentation.view.ProductsFragment
import com.uzias.starwarsshop.transactions.presentation.view.TransactionsFragment

interface MenuNavigation {
    fun goToProducts()
    fun goToTransactions()
}

class MenuNavigationImpl(private var fragmentManager: FragmentManager): MenuNavigation {

    override fun goToProducts() {
        goToFragment(ProductsFragment.newInstance())
    }

    override fun goToTransactions() {
        goToFragment(TransactionsFragment.newInstance())
    }

    private fun goToFragment(fragment: Fragment){
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.content_layout, fragment)
        fragmentTransaction.commit()
    }

}