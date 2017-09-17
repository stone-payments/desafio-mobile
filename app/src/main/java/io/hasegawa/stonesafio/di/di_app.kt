package io.hasegawa.stonesafio.di

import dagger.Component
import dagger.Module
import dagger.Provides
import io.hasegawa.data.test.InMemTestRepository
import io.hasegawa.stonesafio.StonesafioApp
import io.hasegawa.stonesafio.common.log.AndroidLogDevice
import io.hasegawa.stonesafio.common.log.NoOpCrashReportDevice
import io.hasegawa.stonesafio.domain.common.log.CrashReportDevice
import io.hasegawa.stonesafio.domain.common.log.LogDevice
import io.hasegawa.stonesafio.domain.common.log.setAsDefaultForCrashReport
import io.hasegawa.stonesafio.domain.common.log.setAsDefaultForLog
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
}

@Module
class TestModule {
    @Provides
    @AppScope
    fun provideTestRepository(): TestRepository = InMemTestRepository()
}

@AppScope
@Component(modules = arrayOf(AppDIModule::class, TestModule::class))
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
                        .build()
                        .apply {
                            provideLogDevice().setAsDefaultForLog()
                            provideCrashReportDevice().setAsDefaultForCrashReport()
                        }
                        .also { BaseDIComponent.initialize(it) }
            }
        }
    }

    fun provideAppContext(): StonesafioApp
    fun provideLogDevice(): LogDevice
    fun provideCrashReportDevice(): CrashReportDevice
    fun provideTestRepository(): TestRepository
}
