package com.uzias.starwarsshop.core.database

import android.content.Context
import com.uzias.starwarsshop.R
import io.realm.Realm
import io.realm.RealmConfiguration

object RealmDatabase {

    private val TYPE_FILE = ".realm"

    fun configure(context: Context){
        Realm.init(context)
        val realmConfiguration = RealmConfiguration.Builder()
                .name(nameFile(context))
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }

    private fun nameFile(context: Context): String = "%s$TYPE_FILE"
            .format(context.getString(R.string.app_name).trim())

}