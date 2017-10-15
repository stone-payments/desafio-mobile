package jb.project.starwarsshoppinglist

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration


/**
 * Created by Jb on 14/10/2017.
 */
class ShopApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        val config = RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build()

        Realm.setDefaultConfiguration(config)
    }
}