package com.stone.desafiomobile

import com.stone.desafiomobile.utils.formatPriceReal
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Testes para a funçao de formatações de preço
 */
class CurrencyFormatTest {

    /**
     * Testa a formatação
     */
    @Test
    fun formatRealTest() {
        assertEquals("R$120,00", 12000L.formatPriceReal())
    }

    /**
     * Testa a formatação quando há centavos
     */
    @Test
    fun formatRealCentsTest() {
        assertEquals("R$10,50", 1050L.formatPriceReal())
    }

    /**
     * Testa a formatação quando há somente centavos
     */
    @Test
    fun formatRealCentsOnlyTest() {
        assertEquals("R$0,70", 70L.formatPriceReal())
    }
}
