package com.example.pharol.temminstore.util

import java.math.BigDecimal
import java.util.*


fun BigDecimal.toMoneyString(): String {
    val sb = StringBuilder()
    Formatter(sb, Locale.GERMANY).format("R$ %,.2f",this.divide(BigDecimal("100"))) //Converting the price Bigdecimal to a money Format, decided to divide per 100 because after all a shirt doesn't cost 7990
    return sb.toString()
}