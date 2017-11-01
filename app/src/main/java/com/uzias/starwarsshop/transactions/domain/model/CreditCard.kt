package com.uzias.starwarsshop.transactions.domain.model

import com.uzias.starwarsshop.core.domain.InvalidData

data class CreditCard(val name: String = InvalidData.INVALID.getString(),
                      val cardNumber: String = InvalidData.INVALID.getString(),
                      val expiry: String = InvalidData.INVALID.getString(),
                      val cvv: Int = InvalidData.INVALID.getInt())