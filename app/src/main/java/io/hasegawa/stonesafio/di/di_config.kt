package io.hasegawa.stonesafio.di

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

sealed class ConfigDI {
    data class ListingBaseURL(val value: String) : ConfigDI()
    data class PaymentBaseURL(val value: String) : ConfigDI()
}

@Module
class ConfigDIModule {
    @Provides
    @Singleton
    fun provideListingBaseURL() = ConfigDI.ListingBaseURL(
            "https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/")

    @Provides
    @Singleton
    fun providePaymentBaseURL() = ConfigDI.PaymentBaseURL(
            "https://private-80fe34-hasegawastonesafio.apiary-mock.com/")
}

@Component(modules = arrayOf(ConfigDIModule::class))
@Singleton
interface ConfigDIComponent {
    companion object {
        fun build() = DaggerConfigDIComponent
                .builder()
                .build()
    }

    fun getListingBaseURL(): ConfigDI.ListingBaseURL
    fun getPaymentBaseURL(): ConfigDI.PaymentBaseURL
}