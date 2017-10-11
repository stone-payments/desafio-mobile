package com.stone.desafiomobile

import com.stone.desafiomobile.utils.formatPriceReal
import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyFormatTest {
    @Test
    fun formatRealTest() {
        assertEquals("R$120,00", 12000L.formatPriceReal())
    }

    @Test
    fun formatRealCentsTest() {
        assertEquals("R$10,50", 1050L.formatPriceReal())
    }

    @Test
    fun formatRealCentsOnlyTest() {
        assertEquals("R$0,70", 70L.formatPriceReal())
    }
}
