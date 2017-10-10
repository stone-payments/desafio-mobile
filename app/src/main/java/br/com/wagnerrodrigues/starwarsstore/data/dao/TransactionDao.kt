package br.com.wagnerrodrigues.starwarsstore.data.dao

import br.com.wagnerrodrigues.starwarsstore.data.helper.DatabaseHelper
import br.com.wagnerrodrigues.starwarsstore.domain.entity.Transaction
import br.com.wagnerrodrigues.starwarsstore.presentation.BaseApp
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager

class TransactionDao() {

    lateinit var daoManager : Dao<Transaction, Int>

    companion object {
        var dao : TransactionDao? = null

        fun  getInstance() : TransactionDao?{
            if(dao == null){
                dao = TransactionDao()
                dao?.daoManager = DaoManager.createDao(DatabaseHelper.getInstance(BaseApp.getContext())?.connectionSource, Transaction::class.java)
            }

            return dao;
        }
    }

    fun fetchAll() : List<Transaction>? {
        return daoManager.queryForAll();
    }

    fun create(transaction : Transaction) {
        daoManager.create(transaction)
    }

    fun clear() {
        daoManager.deleteBuilder().delete()
    }
}