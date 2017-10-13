package com.stone.desafiomobile.di

import com.stone.desafiomobile.viewmodel.CheckoutVm
import com.stone.desafiomobile.viewmodel.ProductsListVm
import com.stone.desafiomobile.viewmodel.PurchaseListVm
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(RetrofitModule::class, DatabaseModule::class))
interface InjectionComponent {

    fun inject(productsListVm:ProductsListVm): Unit

    fun inject(checkoutVm: CheckoutVm): Unit

    fun inject(purchaseListVm: PurchaseListVm): Unit
}
