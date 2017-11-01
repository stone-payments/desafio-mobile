package com.uzias.starwarsshop.checkout.presentation.model

import com.uzias.starwarsshop.core.domain.InvalidData

data class PresentationCreditCard(val name: String = InvalidData.INVALID.getString(),
                                  val cardNumber: String = InvalidData.INVALID.getString(),
                                  val expiry: String = InvalidData.INVALID.getString(),
                                  val cvv: String = InvalidData.INVALID.getString())