package br.com.wagnerrodrigues.starwarsstore.domain.interactor

import br.com.wagnerrodrigues.starwarsstore.data.dao.TransactionDao
import br.com.wagnerrodrigues.starwarsstore.presentation.event.PurchasesPreparedEvent
import org.greenrobot.eventbus.EventBus

class PurchasesInteractor : Interactor(){

    private val transactionDao: TransactionDao? = TransactionDao.getInstance();

    fun preparePurchases(){
        val transactions = transactionDao?.fetchAll()!!
        EventBus.getDefault().post(PurchasesPreparedEvent(transactions))
    }

}