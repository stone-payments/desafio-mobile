package br.com.stone.vianna.starstore.extensions


import android.view.View

/**
 * View
 */
fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}
