package br.com.stone.vianna.starstore.feature.card

import br.com.stone.vianna.starstore.RxTestRule
import br.com.stone.vianna.starstore.entity.Item
import br.com.stone.vianna.starstore.entity.PaymentRequest
import br.com.stone.vianna.starstore.entity.PaymentTransaction
import br.com.stone.vianna.starstore.entity.TransactionDao
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Spy

class CardRepository {
    @Rule
    @JvmField
    val rule = RxTestRule()


    private val paymentApi: PaymentApi = mock {
        on { checkout(any()) }.thenReturn(Observable.just(transaction))
    }

    private val transactionDao: TransactionDao = mock {
        on { insertTransaction(any()) }.thenReturn(Completable.complete())
        on { getTransactionCount() }.thenReturn(Maybe.just(3))
    }

    @Spy
    private val repository = PaymentRepositoryImpl(paymentApi, transactionDao)


    @Test
    fun `call api when checkout() is called`() {
        repository.checkout(validPaymentRequest).test()

        Mockito.verify(paymentApi).checkout(validPaymentRequest)
    }

    @Test
    fun `call dao when inserTransaction() is called`() {

        repository.saveTransactionLocally(transaction).test()
        Mockito.verify(transactionDao).insertTransaction(transaction)
    }


    companion object {
        val transaction = PaymentTransaction(id = 1, cardHolderName = "Name 1",
                cardLastDigits = "1234", value = 100, transactionDateTime = "11/11/2020")

        val validPaymentRequest = PaymentRequest("4242424242424242",
                "11/20",
                "123",
                "Name 1",
                0)
    }

}