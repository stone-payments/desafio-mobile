package com.uzias.starwarsshop.transactions.presentation.model

import com.uzias.starwarsshop.core.domain.InvalidData
import java.text.DateFormat
import java.text.NumberFormat
import java.util.*

data class PresentationTransaction(val value: Double = InvalidData.UNINITIALIZED.getDouble(),
                                   val date: Date = InvalidData.UNINITIALIZED.getDate(),
                                   val lastFourDigitsCard: String = InvalidData.UNINITIALIZED.getString(),
                                   val name: String = InvalidData.UNINITIALIZED.getString()) {

    fun getValueFormatted() : String {
        return NumberFormat.getCurrencyInstance().format(value)
    }

    fun getDateFormatted(): String {
        return DateFormat.getDateTimeInstance().format(date)
    }

}