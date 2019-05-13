package br.com.stone.vianna.starstore.feature.transactionHistory

import br.com.stone.vianna.starstore.entity.PaymentTransaction
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.subjects.BehaviorSubject
import org.junit.Test

class TransactionHistoryPresenterTest {

    private val transactionsSubject = BehaviorSubject.createDefault(transactionList)
    private val transactionRepository: TransactionRepository = mock {
        on { getTransactionHistory() }.thenReturn(transactionsSubject)
    }

    private val view: TransactionContract.View = mock()
    private val presenter = TransactionPresenter(view, transactionRepository)

    @Test
    fun `fetch transactions from repository when init() is called`() {
        presenter.init()
        verify(transactionRepository).getTransactionHistory()
    }

    @Test
    fun `when init() is called it should update the view`() {
        presenter.init()
        verify(view).updateTransactionHistory(transactionList)
    }

    companion object {
        val transactionList = listOf(
                PaymentTransaction(id = 0, cardHolderName = "Name 1",
                        cardLastDigits = "1234", value = 100, transactionDateTime = "11/11/2018"),
                PaymentTransaction(id = 1, cardHolderName = "Name 2",
                        cardLastDigits = "4321", value = 200, transactionDateTime = "11/11/2019"),
                PaymentTransaction(id = 2, cardHolderName = "Name 3",
                        cardLastDigits = "3214", value = 300, transactionDateTime = "11/11/2020")
        )
    }
}