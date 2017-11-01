package com.uzias.starwarsshop.core.application.di

import com.uzias.starwarsshop.checkout.di.CheckoutModule
import com.uzias.starwarsshop.checkout.presentation.view.CheckoutActivity
import com.uzias.starwarsshop.menu.di.MenuModule
import com.uzias.starwarsshop.menu.presentation.view.MenuActivity
import com.uzias.starwarsshop.products.di.ProductsModule
import com.uzias.starwarsshop.products.presentation.view.ProductsFragment
import com.uzias.starwarsshop.transactions.di.TransactionsModule
import com.uzias.starwarsshop.transactions.presentation.view.TransactionsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBuilder {

    @ContributesAndroidInjector(modules = arrayOf(ProductsModule::class))
    abstract fun productsFragment(): ProductsFragment

    @ContributesAndroidInjector(modules = arrayOf(TransactionsModule::class))
    abstract fun transactionsFragment(): TransactionsFragment

    @ContributesAndroidInjector(modules = arrayOf(MenuModule::class))
    abstract fun menuActivity(): MenuActivity

    @ContributesAndroidInjector(modules = arrayOf(CheckoutModule::class))
    abstract fun checkoutActivity(): CheckoutActivity

}
