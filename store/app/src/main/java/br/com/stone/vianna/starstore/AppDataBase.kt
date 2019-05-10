package br.com.stone.vianna.starstore

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import br.com.stone.cryptowallet.entity.*

@Database(entities = [Item::class, PaymentTransaction::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun transactionDao(): TransactionDao
}