package com.stone.desafiomobile.di

import com.stone.desafiomobile.viewmodel.CheckoutVm
import com.stone.desafiomobile.viewmodel.ProductsListVm
import com.stone.desafiomobile.viewmodel.PurchaseListVm
import dagger.Component
import javax.inject.Singleton

/**
 * Classe que define quem fornecera as dependencias e quem as injetará
 */
@Singleton
@Component(modules = arrayOf(RetrofitModule::class, DatabaseModule::class))
interface InjectionComponent {

    /**
     * Injeta as dependencias
     */
    fun inject(productsListVm: ProductsListVm)

    /**
     * Injeta as dependencias
     */
    fun inject(checkoutVm: CheckoutVm)

    /**
     * Injeta as dependencias
     */
    fun inject(purchaseListVm: PurchaseListVm)
}
