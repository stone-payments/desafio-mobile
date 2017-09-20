package io.hasegawa.stonesafio

import android.support.multidex.MultiDexApplication
import io.hasegawa.stonesafio.di.AppDIComponent

class StonesafioApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        AppDIComponent.initialize(this)
    }

}