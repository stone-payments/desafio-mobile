package douglasspgyn.com.github.desafiostone.common.extensions

import android.view.View

/**
 * Created by Douglas on 12/11/17.
 */

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}