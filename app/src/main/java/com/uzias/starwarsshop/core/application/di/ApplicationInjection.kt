package com.uzias.starwarsshop.core.application.di

import android.app.Application
import android.content.Context
import com.uzias.starwarsshop.core.application.StarWarsShopApplication
import com.uzias.starwarsshop.core.networking.di.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, ApplicationModule::class, NetworkModule::class, ActivitiesBuilder::class))
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent

    }

    fun inject(target: StarWarsShopApplication)

}

@Module
class ApplicationModule {

    @Provides
    fun providesContext(application: Application): Context = application

    @Provides
    @UIScheduler
    fun providesUiScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @IOScheduler
    fun providesIoScheduler(): Scheduler = Schedulers.io()

}
