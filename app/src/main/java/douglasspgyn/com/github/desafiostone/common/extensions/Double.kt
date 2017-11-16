package douglasspgyn.com.github.desafiostone.common.extensions

import java.text.NumberFormat

/**
 * Created by Douglas on 12/11/17.
 */

fun Double.toCurrency(): String {
    return NumberFormat.getCurrencyInstance().format(this / 100)
}