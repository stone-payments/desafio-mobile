package br.com.ygorcesar.desafiostone

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val mRealmConfiguration =  RealmConfiguration.Builder()
                .name("DesafioMobile.realm")
                .schemaVersion(1)
                .build()
        Realm.setDefaultConfiguration(mRealmConfiguration)
    }
}