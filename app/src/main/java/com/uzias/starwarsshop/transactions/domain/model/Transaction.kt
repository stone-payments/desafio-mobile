package com.uzias.starwarsshop.transactions.domain.model

import com.uzias.starwarsshop.core.domain.InvalidData
import java.util.*

data class Transaction(val value: Double = InvalidData.UNINITIALIZED.getDouble(),
                   val date: Date = InvalidData.UNINITIALIZED.getDate(),
                   var lastFourDigitsCard: String = InvalidData.UNINITIALIZED.getString(),
                   val name: String = InvalidData.UNINITIALIZED.getString())