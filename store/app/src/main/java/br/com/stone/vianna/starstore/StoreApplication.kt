package br.com.stone.vianna.starstore

import android.app.Application
import br.com.stone.vianna.starstore.di.applicationModule
import br.com.stone.vianna.starstore.di.daoModule
import br.com.stone.vianna.starstore.di.presenterModule
import br.com.stone.vianna.starstore.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class StoreApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@StoreApplication)
            modules(applicationModule,
                    repositoryModule,
                    presenterModule,
                    daoModule)
        }
    }
}