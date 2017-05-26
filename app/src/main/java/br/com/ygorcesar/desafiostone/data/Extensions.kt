package br.com.ygorcesar.desafiostone.data

import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).show()
}

fun Fragment.toast(@StringRes idRes: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, idRes, duration).show()
}

fun EditText.getString() = this.text.toString()


fun Date.formatToBrasil(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(this)
}

fun Double.toCurrencyBRL(): String {
    return NumberFormat.getCurrencyInstance().format(this)
}

fun String.lastFourDigits() = this.substring(this.length - 4)

fun ImageView.loadImage(url: String) {
    Glide.with(this.context)
            .load(url)
            .centerCrop()
            .into(this)
}