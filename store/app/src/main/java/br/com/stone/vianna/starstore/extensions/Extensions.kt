package br.com.stone.vianna.starstore.extensions

import android.view.View
import br.com.stone.vianna.starstore.R
import br.com.stone.vianna.starstore.StoreApplication
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
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
 * Handle Error
 */

data class ResponseParser(val success: Boolean, val error: String, val code: Int = 404)

val Throwable.parser: ResponseParser
    get() {
        if (this !is HttpException) {
            return ResponseParser(false, R.string.error_title.translate)
        }

        val strError = this.response().errorBody()?.string()
                ?: return ResponseParser(false, R.string.error_title.translate, this.response().code())

        val gson = Gson()
        val typeAdapter = gson.getAdapter(ResponseParser::class.java)
        return try {
            typeAdapter.fromJson(strError)
        } catch (e: IOException) {
            ResponseParser(false, R.string.error_title.translate, this.response().code())
        }
    }

/**
 * Translate
 */

val Int.translate: String
    get() {
        return StoreApplication.context.getString(this) ?: ""
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