package br.com.stone.vianna.starstore.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(transaction: PaymentTransaction): Completable

    @Query("SELECT * FROM PaymentTransaction")
    fun getTransactions(): Maybe<List<PaymentTransaction>>

    @Query("SELECT COUNT(*) FROM PaymentTransaction")
    fun getTransactionCount(): Maybe<Int>

}