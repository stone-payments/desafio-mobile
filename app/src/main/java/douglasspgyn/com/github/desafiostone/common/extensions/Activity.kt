package douglasspgyn.com.github.desafiostone.common.extensions

import android.app.Activity
import android.support.design.widget.Snackbar

/**
 * Created by Douglas on 12/11/17.
 */

fun Activity.snackbar(text: String, long: Boolean = false) {
    if (long) {
        longSnackbar(text)
    } else {
        shortSnackbar(text)
    }
}

private fun Activity.shortSnackbar(text: String) {
    Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show()
}

private fun Activity.longSnackbar(text: String) {
    Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG).show()
}