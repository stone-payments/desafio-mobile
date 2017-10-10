package br.com.wagnerrodrigues.starwarsstore.data.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import br.com.wagnerrodrigues.starwarsstore.domain.entity.CartItem
import br.com.wagnerrodrigues.starwarsstore.domain.entity.Transaction
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import java.sql.SQLException


class DatabaseHelper(context: Context) : OrmLiteSqliteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private var helperInstance : DatabaseHelper? = null
        private val DATABASE_NAME = "sws.db"
        private val DATABASE_VERSION = 1

        @Synchronized
        fun getInstance(context: Context): DatabaseHelper? {

            if (helperInstance == null) {
                helperInstance = DatabaseHelper(context)
            }

            return helperInstance
        }

    }



    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    override fun onCreate(db: SQLiteDatabase, connectionSource: ConnectionSource) {
        try {
            TableUtils.createTable<CartItem>(connectionSource, CartItem::class.java)
            TableUtils.createTable<Transaction>(connectionSource, Transaction::class.java)
        } catch (e: SQLException) {
            Log.e(DatabaseHelper::class.java.name, "Can't create database", e)
            throw RuntimeException(e)
        }
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    override fun onUpgrade(db: SQLiteDatabase, connectionSource: ConnectionSource, oldVersion: Int, newVersion: Int) {
        try {
            Log.i(DatabaseHelper::class.java.name, "onUpgrade")

            onCreate(db, connectionSource)
        } catch (e: SQLException) {
            Log.e(DatabaseHelper::class.java.name, "Can't drop databases", e)
            throw RuntimeException(e)
        }

    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    override fun close() {
        super.close()
    }

}