package douglasspgyn.com.github.desafiostone.common.extensions

import android.widget.EditText
import java.util.*

/**
 * Created by Douglas on 13/11/17.
 */

fun EditText.validCreditCardNumber(): Boolean {
    val text = this.text.toString().replace(" ", "")

    var sum = 0
    var alternate = false

    for (i in text.length - 1 downTo 0) {
        var num = Integer.parseInt(text.substring(i, i + 1))
        if (alternate) {
            num *= 2
            if (num > 9) {
                num = num % 10 + 1
            }
        }
        sum += num
        alternate = !alternate
    }

    return sum % 10 == 0
}

fun EditText.validCreditCardExpiresDate(): Boolean {
    val text = this.text.toString()
    val split = text.split("/")

    if (text.length < 5) {
        return false
    }

    if (split.size != 2) {
        return false
    } else if (Integer.parseInt(split[0]) < 1 || Integer.parseInt(split[0]) > 12) {
        return false
    }

    val validity = Calendar.getInstance()
    validity.set(2000 + Integer.parseInt(split[1]), Integer.parseInt(split[0]) - 1, 1)

    val current = Calendar.getInstance()

    return validity.timeInMillis > current.timeInMillis
}