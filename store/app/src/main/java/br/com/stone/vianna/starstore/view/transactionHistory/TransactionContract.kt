package br.com.stone.vianna.starstore.view.transactionHistory

import br.com.stone.vianna.starstore.entity.PaymentTransaction

class TransactionContract {

    interface View {
        fun updateTransactionHistory(transactions: List<PaymentTransaction>)
    }


    interface Presenter {
        fun init()
    }
}