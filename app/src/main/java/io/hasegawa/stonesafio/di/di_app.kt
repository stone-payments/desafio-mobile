package io.hasegawa.stonesafio.di

import dagger.Component
import dagger.Module
import dagger.Provides
import io.hasegawa.data.cart.InMemCartRepository
import io.hasegawa.data.listing.RetrofitListingService
import io.hasegawa.data.payment.InMemPaymentService
import io.hasegawa.data.payment.InMemTransactionRepository
import io.hasegawa.data.test.InMemTestRepository
import io.hasegawa.stonesafio.StonesafioApp
import io.hasegawa.stonesafio.common.log.AndroidLogDevice
import io.hasegawa.stonesafio.common.log.NoOpCrashReportDevice
import io.hasegawa.stonesafio.domain.cart.CartRepository
import io.hasegawa.stonesafio.domain.cc.CCValidatorDevice
import io.hasegawa.stonesafio.domain.cc.SimpleCCValidatorDevice
import io.hasegawa.stonesafio.domain.common.log.CrashReportDevice
import io.hasegawa.stonesafio.domain.common.log.LogDevice
import io.hasegawa.stonesafio.domain.common.log.setAsDefaultForCrashReport
import io.hasegawa.stonesafio.domain.common.log.setAsDefaultForLog
import io.hasegawa.stonesafio.domain.listing.ListingService
import io.hasegawa.stonesafio.domain.payment.PaymentService
import io.hasegawa.stonesafio.domain.payment.TransactionRepository
import io.hasegawa.stonesafio.domain.test.TestRepository
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope

@Module
open class AppDIModule(private val app: StonesafioApp) {
    @Provides
    @AppScope
    open fun provideAppContext(): StonesafioApp = app

    @Provides
    @AppScope
    open fun provideLogDevice(): LogDevice = AndroidLogDevice()

    @Provides
    @AppScope
    open fun provideCrashReportDevice(): CrashReportDevice = NoOpCrashReportDevice()

    @Provides
    @AppScope
    open fun provideListingService(baseURL: ConfigDI.ListingBaseURL): ListingService =
            RetrofitListingService(baseURL.value)

    @Provides
    @AppScope
    open fun provideCartRepository(): CartRepository = InMemCartRepository()

    @Provides
    @AppScope
    open fun provideCCValidator(): CCValidatorDevice = SimpleCCValidatorDevice()

    @Provides
    @AppScope
    open fun providePaymentService(): PaymentService = InMemPaymentService()

    @Provides
    @AppScope
    open fun provideTransactionRepository(): TransactionRepository = InMemTransactionRepository()
}

@Module
class TestModule {
    @Provides
    @AppScope
    fun provideTestRepository(): TestRepository = InMemTestRepository()
}

@AppScope
@Component(modules = arrayOf(AppDIModule::class, TestModule::class),
        dependencies = arrayOf(ConfigDIComponent::class))
interface AppDIComponent {
    companion object {
        lateinit var instance: AppDIComponent
            private set

        fun initialize(app: StonesafioApp) {
            try {
                instance
            } catch (e: UninitializedPropertyAccessException) {
                instance = DaggerAppDIComponent
                        .builder()
                        .appDIModule(AppDIModule(app))
                        .configDIComponent(ConfigDIComponent.build())
                        .build()
                        .apply {
                            getLogDevice().setAsDefaultForLog()
                            getCrashReportDevice().setAsDefaultForCrashReport()
                        }
                        .also { BaseDIComponent.initialize(it) }
            }
        }
    }

    fun getAppContext(): StonesafioApp
    fun getLogDevice(): LogDevice
    fun getCrashReportDevice(): CrashReportDevice
    fun getTestRepository(): TestRepository
    fun getListingService(): ListingService
    fun getCartRepository(): CartRepository
    fun getCCValidator(): CCValidatorDevice
    fun getPaymentService(): PaymentService
    fun getTransactionRepository(): TransactionRepository
}
