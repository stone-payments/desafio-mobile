package br.com.stone.vianna.starstore.entity

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(transaction: PaymentTransaction)

    @Query("SELECT * FROM PaymentTransaction")
    fun getTransactions(): Maybe<List<PaymentTransaction>>

    @Query("SELECT COUNT(*) FROM PaymentTransaction")
    fun getTransactionCount(): Maybe<Int>

}