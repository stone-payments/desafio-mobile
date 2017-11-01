package com.uzias.starwarsshop.checkout.presentation.mapper

import com.uzias.starwarsshop.checkout.presentation.model.PresentationCreditCard
import com.uzias.starwarsshop.transactions.domain.model.CreditCard
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import junit.framework.Assert.assertEquals

@RunWith(MockitoJUnitRunner::class)
class PresentationCreditCardMapperTest {

    val fakeCreditCard = CreditCard(name = "Teste 1", cardNumber = "1111 1111 1111 1111", expiry = "01/01", cvv = 459)
    val fakePresentation = PresentationCreditCard(name = "Teste 2",
            cardNumber = "2222 1111 1111 1111", expiry = "01/09", cvv = "333")

    @Test
    fun correctly_transform_presentation_to_domain() {
        val domain = PresentationCreditCardMapper.transformFrom(fakePresentation)
        assertEquals(fakePresentation.cardNumber, domain.cardNumber)
        assertEquals(fakePresentation.name, domain.name)
        assertEquals(fakePresentation.expiry, domain.expiry)
        assertEquals(fakePresentation.cvv.toInt(), domain.cvv)
    }

    @Test
    fun correctly_transform_domain_to_presentation() {
        val presentation = PresentationCreditCardMapper.transformTo(fakeCreditCard)
        assertEquals(fakeCreditCard.cardNumber, presentation.cardNumber)
        assertEquals(fakeCreditCard.name, presentation.name)
        assertEquals(fakeCreditCard.expiry, presentation.expiry)
        assertEquals(fakeCreditCard.cvv.toString(), presentation.cvv)
    }

}