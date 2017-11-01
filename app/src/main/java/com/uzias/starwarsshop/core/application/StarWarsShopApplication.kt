package com.uzias.starwarsshop.core.application

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import com.uzias.starwarsshop.core.application.di.DaggerApplicationComponent
import com.uzias.starwarsshop.core.database.RealmDatabase
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

class StarWarsShopApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>


    override fun onCreate() {
        super.onCreate()
        injectDependencies()
        RealmDatabase.configure(this)
    }

    private fun injectDependencies() {
        DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
                .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector

}