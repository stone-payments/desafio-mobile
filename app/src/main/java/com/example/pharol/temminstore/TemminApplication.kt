package com.example.pharol.temminstore

import android.app.Application
import com.example.pharol.temminstore.di.ApplicationComponent
import com.example.pharol.temminstore.di.DaggerApplicationComponent
import pharol.com.br.temminstore.di.ApplicationModule
import javax.inject.Inject

//import pharol.com.br.temminstore.di


class TemminApplication : Application(){

    @Inject lateinit var webService : WebService

    val applicationComponent : ApplicationComponent = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()

    override fun onCreate() {
        super.onCreate()
        applicationComponent.inject(this)
    }
}


