package io.hasegawa.stonesafio.screen_transactions

import io.hasegawa.presentation.screen_transactions.TransactionsContract

class TransactionNavigator(val controller: TransactionsController) : TransactionsContract.Navigator {
    override fun goBackToStore() {
        controller.router.popCurrentController()
    }
}