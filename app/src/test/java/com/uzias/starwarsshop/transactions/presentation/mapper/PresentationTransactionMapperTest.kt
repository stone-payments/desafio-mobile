package com.uzias.starwarsshop.transactions.presentation.mapper

import com.uzias.starwarsshop.transactions.domain.model.Transaction
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class PresentationTransactionMapperTest {

    val fakeTransaction = Transaction(value = 99.00, date = Date(0), lastFourDigitsCard = "2222", name = "Teste")

    @Test
    fun correctly_transform_domain_to_presentation() {
        val presentation = PresentationTransactionMapper.transformTo(fakeTransaction)
        Assert.assertEquals(fakeTransaction.value, presentation.value)
        Assert.assertEquals(fakeTransaction.date, presentation.date)
        Assert.assertEquals(fakeTransaction.name, presentation.name)
        Assert.assertEquals(fakeTransaction.lastFourDigitsCard, presentation.lastFourDigitsCard)
    }

}