package com.uzias.starwarsshop.transactions.data.datasource.database

import com.uzias.starwarsshop.core.errors.ContentNotFoundError
import com.uzias.starwarsshop.transactions.data.datasource.database.mapper.RealmTransactionMapper
import com.uzias.starwarsshop.transactions.data.datasource.database.model.RealmTransaction
import com.uzias.starwarsshop.transactions.domain.model.Transaction
import io.reactivex.Flowable
import io.realm.Realm

interface TransactionsDatabase {
    fun getTransactions() : Flowable<List<Transaction>>
    fun addTransaction(transaction: Transaction) : Flowable<Transaction>
}

class TransactionsDatabaseImpl : TransactionsDatabase {

    override fun addTransaction(transaction: Transaction) : Flowable<Transaction> {
        if (transaction.lastFourDigitsCard.length > 4){
            var card = transaction.lastFourDigitsCard
            card = card.substring(card.length - 4)
            transaction.lastFourDigitsCard = card
        }
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.copyToRealm(RealmTransactionMapper.transformFrom(transaction))
        realm.commitTransaction()
        realm.close()
        return Flowable.just(transaction)
    }

    override fun getTransactions(): Flowable<List<Transaction>> {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val list = realm.copyFromRealm(realm.where(RealmTransaction::class.java).findAll().toList())
        realm.commitTransaction()
        realm.close()
        if (list.isEmpty()){
            return Flowable.error(ContentNotFoundError())
        }

        return Flowable.just(RealmTransactionMapper.transformToList(list))
    }

}