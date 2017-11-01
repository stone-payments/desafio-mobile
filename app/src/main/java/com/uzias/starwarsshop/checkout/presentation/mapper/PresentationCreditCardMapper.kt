package com.uzias.starwarsshop.checkout.presentation.mapper

import com.uzias.starwarsshop.checkout.presentation.model.PresentationCreditCard
import com.uzias.starwarsshop.core.mapper.BaseMapper
import com.uzias.starwarsshop.transactions.domain.model.CreditCard

object PresentationCreditCardMapper : BaseMapper<CreditCard, PresentationCreditCard>() {

    override fun transformTo(source: CreditCard): PresentationCreditCard = PresentationCreditCard(
            name = source.name, cardNumber = source.cardNumber, expiry = source.expiry,
            cvv = source.cvv.toString())

    override fun transformFrom(source: PresentationCreditCard): CreditCard = CreditCard(
            name = source.name, cardNumber = source.cardNumber, expiry = source.expiry,
            cvv = source.cvv.toInt())

}