package br.com.stone.vianna.starstore.extensions

import java.text.NumberFormat
import java.util.*

/**
 * Form Helper
 */

fun String.isValidNameLength(): Boolean {
    return this.length > 2
}

fun String.isValidCardLength(): Boolean {
    return this.length == 16
}

fun String.isValidCVVLength(): Boolean {
    return this.length == 3
}

/**
* Money
*/
fun Int.toMoneyFormat(): String {

    val currency = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    val value = currency.format(this / 100.0)
    return value
}