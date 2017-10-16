package com.stone.desafiomobile.utils

import android.widget.EditText
import com.stone.desafiomobile.R

/**
 * valida se o input esta vazio
 * @return objeto para continuar mais validações ou nulo se a validação falhou
 */
fun EditText.validateEmpty(): EditText? {
    if (this.text.isEmpty()) {
        this.setError(resources.getString(R.string.empty_error_text))
        this.requestFocus()
        return null
    }
    return this
}

/**
 * valida se o input tem o tamanho necessario
 * @param minLength valor minimo que o input deve possuir
 * @return objeto para continuar mais validações ou nulo se a validação falhou
 */
fun EditText.validateMinLength(minLength: Int): EditText? {
    if (this.text.length < minLength) {
        val message = String.format(resources.getString(R.string.min_length_error_text), minLength);
        this.setError(message)
        this.requestFocus()
        return null
    }
    return this
}

/**
 * valida se o input segue o padrão MM/yy
 * @return objeto para continuar mais validações ou nulo se a validação falhou
 */
fun EditText.validateDate(): EditText? {
    if (!this.text.matches(Regex("[0-1][0-9]/[0-9][0-9]")) || this.text.take(2).toString().toLong() > 12) {
        val message = resources.getString(R.string.invalid_format_error_text);
        this.setError(message)
        this.requestFocus()
        return null
    }
    return this
}


