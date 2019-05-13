package br.com.stone.vianna.starstore.feature.card

import br.com.stone.vianna.starstore.entity.PaymentRequest
import br.com.stone.vianna.starstore.entity.PaymentTransaction
import br.com.stone.vianna.starstore.feature.itemList.ItemListRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.subjects.BehaviorSubject
import org.junit.Test

class CardPresenterTest {

    private val transactionSubject = BehaviorSubject.createDefault(transaction)
    private val paymentRepository: PaymentRepository = mock {
        on { checkout(any()) }.thenReturn(transactionSubject)
    }
    private val itemListRepository: ItemListRepository = mock {}


    private val view: CardContract.View = mock()
    private val presenter = CardPresenter(view, paymentRepository, itemListRepository)


    @Test
    fun `when checkout button is clicked sending a valid PaymentRequest checkout must be called`() {

        presenter.onCheckoutButtonClicked("4242424242424242",
                "Name 1",
                "11/20",
                "123")

        verify(paymentRepository).checkout(validPaymentRequest)
    }

    @Test
    fun `when card number has less than 16 digits it should display error message`() {
        presenter.onCheckoutButtonClicked("42424242",
                "Name 1",
                "11/20",
                "123")

        verify(view).displayCardNumberError()
        verify(paymentRepository, times(0)).checkout(any())
    }

    @Test
    fun `when card number is empty it should display error message`() {
        presenter.onCheckoutButtonClicked("",
                "Name 1",
                "11/20",
                "123")

        verify(view).displayCardNumberError()
        verify(paymentRepository, times(0)).checkout(any())
    }

    @Test
    fun `when card expiration date is empty it should display error message`() {
        presenter.onCheckoutButtonClicked("4242424242424242",
                "Name 1",
                "",
                "123")

        verify(view).displayCardExpirationDateError()
        verify(paymentRepository, times(0)).checkout(any())
    }

    @Test
    fun `when card expiration date is not valid it should display error message`() {
        presenter.onCheckoutButtonClicked("4242424242424242",
                "Name 1",
                "11/90",
                "123")

        verify(view).displayCardExpirationDateError()
        verify(paymentRepository, times(0)).checkout(any())
    }

    @Test
    fun `when card expiration date is past due it should display error message`() {
        presenter.onCheckoutButtonClicked("4242424242424242",
                "Name 1",
                "11/17",
                "123")

        verify(view).displayCardExpirationDateError()
        verify(paymentRepository, times(0)).checkout(any())
    }

    @Test
    fun `when card holder name is empty it should display error message`() {
        presenter.onCheckoutButtonClicked("4242424242424242",
                "",
                "11/20",
                "123")

        verify(view).displayCardHolderError()
        verify(paymentRepository, times(0)).checkout(any())
    }

    @Test
    fun `when card holder name has less than 2 letters it should display error message`() {
        presenter.onCheckoutButtonClicked("4242424242424242",
                "Me",
                "11/20",
                "123")

        verify(view).displayCardHolderError()
        verify(paymentRepository, times(0)).checkout(any())
    }

    @Test
    fun `when cvv is empty it should display error message`() {
        presenter.onCheckoutButtonClicked("4242424242424242",
                "Name 1",
                "11/20",
                "")

        verify(view).displayCardCvvError()
        verify(paymentRepository, times(0)).checkout(any())
    }

    @Test
    fun `when cvv has less than 3 digits it should display error message`() {
        presenter.onCheckoutButtonClicked("4242424242424242",
                "Name 1",
                "11/20",
                "1")

        verify(view).displayCardCvvError()
        verify(paymentRepository, times(0)).checkout(any())
    }

    private companion object {
        val transaction = PaymentTransaction(id = 1, cardHolderName = "Name 1",
                cardLastDigits = "1234", value = 100, transactionDateTime = "11/11/2020")

        val validPaymentRequest = PaymentRequest("4242424242424242",
                "11/20",
                "123",
                "Name 1",
                0)

    }
}