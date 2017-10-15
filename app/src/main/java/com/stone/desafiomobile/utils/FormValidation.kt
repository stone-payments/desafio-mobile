package com.stone.desafiomobile.utils

import android.widget.EditText
import com.stone.desafiomobile.R

fun EditText.validateEmpty(): EditText? {
    if (this.text.isEmpty()) {
        this.setError(resources.getString(R.string.empty_error_text))
        this.requestFocus()
        return null
    }
    return this
}

fun EditText.validateMinLength(minLength: Int): EditText? {
    if (this.text.length < minLength) {
        val message = String.format(resources.getString(R.string.min_length_error_text), minLength);
        this.setError(message)
        this.requestFocus()
        return null
    }
    return this
}


fun EditText.validateDate(): EditText? {
    if (!this.text.matches(Regex("[0-1][0-9]/[0-9][0-9]")) || this.text.take(2).toString().toLong() > 12) {
        val message = resources.getString(R.string.invalid_format_error_text);
        this.setError(message)
        this.requestFocus()
        return null
    }
    return this
}


