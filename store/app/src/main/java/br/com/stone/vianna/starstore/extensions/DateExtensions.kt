package br.com.stone.vianna.starstore.extensions

import java.text.SimpleDateFormat
import java.util.*

/**
 * Date Format
 */
fun isDateExpired(date: String): Boolean {

    val simpleDateFormat = SimpleDateFormat("MM/yy")
    simpleDateFormat.isLenient = false
    val expiry = simpleDateFormat.parse(date)
    val expired = expiry.before(Date())
    return expired
}

fun validateCardExpiryDate(expiryDate: String): Boolean {
    return expiryDate.matches("(?:0[1-9]|1[0-2])/[0-9]{2}".toRegex())
}

/**
 * Date
 */

fun convertDateToFormat(date: String, oldFormat: String, newFormat: String): String {
    val initialFormat = SimpleDateFormat(oldFormat)
    val format = SimpleDateFormat(newFormat)
    val finalDate = initialFormat.parse(date)
    return format.format(finalDate).toString()
}