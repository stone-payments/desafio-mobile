package com.partiufast.mercadodoimperador

import android.database.sqlite.SQLiteDatabase
import com.partiufast.mercadodoimperador.ui.App
import org.jetbrains.anko.db.*

/**
 * Created by miguel on 03/06/17.
 */
class BillDbHelper() : ManagedSQLiteOpenHelper(App.instance, DB_NAME, null, DB_VERSION){

    companion object {
        val DB_NAME = "bill.db"
        val DB_VERSION = 1
        val instance by lazy { BillDbHelper() }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(BillHistoryTable.NAME, true,
                BillHistoryTable.ID to INTEGER + PRIMARY_KEY,
                BillHistoryTable.CARD_OWNER to TEXT,
                BillHistoryTable.LAST_DIGITS_CARD to INTEGER,
                BillHistoryTable.DATE_TIME to TEXT,
                BillHistoryTable.DATE_TIME to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(BillHistoryTable.NAME, true)
    }


}