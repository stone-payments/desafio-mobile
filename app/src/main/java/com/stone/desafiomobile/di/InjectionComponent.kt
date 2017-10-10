package com.stone.desafiomobile.di

import com.stone.desafiomobile.viewmodel.ProductsListVm
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(RetrofitModule::class, DatabaseModule::class))
interface InjectionComponent {

    fun inject(productsListVm:ProductsListVm): Unit
}
