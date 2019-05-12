package br.com.stone.vianna.starstore.feature.transactionHistory

import br.com.stone.vianna.starstore.RxTestRule
import br.com.stone.vianna.starstore.entity.PaymentTransaction
import br.com.stone.vianna.starstore.entity.TransactionDao
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Spy

class TransactionRepositoryTest {

    @Rule
    @JvmField
    val rule = RxTestRule()

    private val transactionDao: TransactionDao = mock {
        on { getTransactions() }.thenReturn(Observable.just(transactionList))
    }

    @Spy
    private val repository = TransactionRepositoryImpl(transactionDao)

    @Test
    fun `call dao when getTotalOfItems() is called`() {
        repository.getTransactionHistory().test()

        Mockito.verify(transactionDao).getTransactions()
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