package douglasspgyn.com.github.desafiostone.common.extensions

import android.app.Activity
import android.support.design.widget.Snackbar
import android.view.View

/**
 * Created by Douglas on 12/11/17.
 */

fun Activity.snackbar(text: String, long: Boolean = false, view: View = findViewById(android.R.id.content)) {
    if (long) {
        longSnackbar(view, text)
    } else {
        shortSnackbar(view, text)
    }
}

private fun Activity.shortSnackbar(view:View, text: String) {
    Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
}

private fun Activity.longSnackbar(view:View, text: String) {
    Snackbar.make(view, text, Snackbar.LENGTH_LONG).show()
}