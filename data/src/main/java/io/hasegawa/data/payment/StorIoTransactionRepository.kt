package io.hasegawa.data.payment

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.lvla.rxjava.interopkt.toV2Completable
import com.lvla.rxjava.interopkt.toV2Observable
import com.pushtorefresh.storio.sqlite.StorIOSQLite
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite
import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver
import com.pushtorefresh.storio.sqlite.operations.put.DefaultPutResolver
import com.pushtorefresh.storio.sqlite.queries.InsertQuery
import com.pushtorefresh.storio.sqlite.queries.Query
import com.pushtorefresh.storio.sqlite.queries.UpdateQuery
import io.hasegawa.stonesafio.domain.common.log.loge
import io.hasegawa.stonesafio.domain.payment.TransactionModel
import io.hasegawa.stonesafio.domain.payment.TransactionRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class StorIoTransactionRepository(context: Context) : TransactionRepository {
    private val db: StorIOSQLite

    init {
        val helper = StorIoTransactionDBHelper(context)
        db = DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(helper)
                .build()
    }

    override fun insert(transaction: TransactionModel): Completable {
        return db.put()
                .`object`(transaction)
                .withPutResolver(TransactionsTable.putResolver)
                .prepare()
                .asRxCompletable()
                .toV2Completable()
                .subscribeOn(Schedulers.io())
                .doOnError { loge(it) { "Error inserting transaction" } }
    }

    override fun getTransactions(): Observable<List<TransactionModel>> {
        return db.get()
                .listOfObjects(TransactionModel::class.java)
                .withQuery(Query.builder().table(TransactionsTable.NAME).build())
                .withGetResolver(TransactionsTable.getResolver)
                .prepare()
                .asRxObservable()
                .toV2Observable()
                .subscribeOn(Schedulers.io())
                .doOnError { loge(it) { "Error fetching transactions" } }
    }
}

private object TransactionsTable {
    val NAME = "transactions"

    enum class COL(val column: String, val properties: String) {
        Id("id", "TEXT NOT NULL"),
        Value("value", "INTEGER NOT NULL"),
        Instant("instant", "INTEGER NOT NULL"),
        CC4LastDigits("cc_4_last_digits", "TEXT NOT NULL"),
        CCName("cc_name", "TEXT NOT NULL");

        override fun toString(): String = this.column
    }

    val CREATE =
            "CREATE TABLE $NAME (" +
                    COL.values().map { "$it ${it.properties}," }.joinToString("\n") +
                    "PRIMARY KEY (${COL.Id}))"

    val putResolver = object : DefaultPutResolver<TransactionModel>() {
        override fun mapToInsertQuery(o: TransactionModel): InsertQuery =
                InsertQuery.builder().table(NAME).build()

        override fun mapToContentValues(o: TransactionModel): ContentValues {
            val c = ContentValues(COL.values().size)
            c.put(COL.Id.column, o.id)
            c.put(COL.Value.column, o.value)
            c.put(COL.Instant.column, o.instant)
            c.put(COL.CC4LastDigits.column, o.ccLast4Digits)
            c.put(COL.CCName.column, o.ccName)
            return c
        }

        override fun mapToUpdateQuery(o: TransactionModel): UpdateQuery =
                UpdateQuery.builder().table(NAME).where("${COL.Id}=?")
                        .whereArgs(o.id)
                        .build()
    }

    val getResolver = object : DefaultGetResolver<TransactionModel>() {
        override fun mapFromCursor(c: Cursor): TransactionModel {
            return TransactionModel(
                    id = c.getString(COL.Id.ordinal),
                    value = c.getLong(COL.Value.ordinal),
                    instant = c.getLong(COL.Instant.ordinal),
                    ccLast4Digits = c.getString(COL.CC4LastDigits.ordinal),
                    ccName = c.getString(COL.CCName.ordinal)
            )
        }
    }
}

private class StorIoTransactionDBHelper(context: Context)
    : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private val DB_NAME = "transactions_db.db"
        private val DB_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(TransactionsTable.CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        throw UnsupportedOperationException("No upgrade yet")
    }
}
