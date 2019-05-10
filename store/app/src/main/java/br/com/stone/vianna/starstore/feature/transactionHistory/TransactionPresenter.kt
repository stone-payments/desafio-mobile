package br.com.stone.vianna.starstore.feature.transactionHistory

class TransactionPresenter(val view: TransactionContract.View,
                           private val transactionRepository: TransactionRepository)
    : TransactionContract.Presenter {

    override fun init() {
        getTransactionHistory()
    }

    private fun getTransactionHistory() {

        transactionRepository.getTransactionHistory {
            view.updateTransactionHistory(it)
        }
    }
}