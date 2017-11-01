package com.uzias.starwarsshop.core.application.di

import com.uzias.starwarsshop.core.networking.di.NetworkModuleTest
import com.uzias.starwarsshop.products.data.repository.datasource.networking.request.GetProducts
import com.uzias.starwarsshop.transactions.data.datasource.networking.request.AddTransaction
import dagger.Component
import javax.inject.Singleton
import dagger.BindsInstance
import javax.inject.Named

@Singleton
@Component(modules = arrayOf(NetworkModuleTest::class))
interface ApplicationComponentTest {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun networkModuleUrl(@Named("baseUrlTest") baseUrl: String): Builder

        fun build(): ApplicationComponentTest

    }

    fun inject(target: GetProducts)
    fun inject(target: AddTransaction)

}