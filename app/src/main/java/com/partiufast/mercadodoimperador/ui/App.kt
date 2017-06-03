package com.partiufast.mercadodoimperador.ui

import android.app.Application
import android.database.sqlite.SQLiteOpenHelper
import com.partiufast.mercadodoimperador.BillDbHelper

class App: Application() {
    companion object {
        lateinit var instance: App
    }

    val database: SQLiteOpenHelper by lazy {
        BillDbHelper()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        val db = database.writableDatabase
    }


}