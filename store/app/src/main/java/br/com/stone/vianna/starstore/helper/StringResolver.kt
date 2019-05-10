package br.com.stone.vianna.starstore.helper

import android.content.Context
import br.com.stone.vianna.starstore.StoreApplication

class StringResolver(val context: Context) {

    fun resolveString(resId: Int): String {
        return StoreApplication.context.getString(resId) ?: ""
    }
}