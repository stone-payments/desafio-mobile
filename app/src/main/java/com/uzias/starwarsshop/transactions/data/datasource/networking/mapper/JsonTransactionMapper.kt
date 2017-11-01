package com.uzias.starwarsshop.transactions.data.datasource.networking.mapper

import com.uzias.starwarsshop.core.mapper.BaseMapper
import com.uzias.starwarsshop.transactions.data.datasource.networking.json.JsonTransaction
import com.uzias.starwarsshop.transactions.domain.model.CreditCard
import com.uzias.starwarsshop.transactions.domain.model.Transaction

object JsonTransactionMapper : BaseMapper<JsonTransaction, Transaction>() {

    override fun transformFrom(source: Transaction): JsonTransaction {
        TODO("not implemented")
    }

    fun transformFrom(creditCard: CreditCard, totalPrice: Double): JsonTransaction =
            JsonTransaction(value = totalPrice, cardNumber = creditCard.cardNumber,
                    name = creditCard.name, expiry = creditCard.expiry)

    override fun transformTo(source: JsonTransaction): Transaction = Transaction(value = source.value,
            lastFourDigitsCard = source.cardNumber, name = source.name)

}
