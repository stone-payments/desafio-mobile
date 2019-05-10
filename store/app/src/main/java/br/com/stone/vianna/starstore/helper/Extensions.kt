package br.com.stone.vianna.starstore.helper

import android.view.View
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * View
 */
fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

/**
 * Observable
 */
fun <T> Observable<T>.addThreads(): Observable<T> {
    return this
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

/**
 * Money
 */
fun Int.toMoneyFormat(): String {

    val currency = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    val value = currency.format(this / 100.0)
    return value
}

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