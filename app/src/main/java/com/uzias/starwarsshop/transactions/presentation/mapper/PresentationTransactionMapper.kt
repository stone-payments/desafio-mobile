package com.uzias.starwarsshop.transactions.presentation.mapper

import com.uzias.starwarsshop.core.mapper.BaseMapper
import com.uzias.starwarsshop.transactions.domain.model.Transaction
import com.uzias.starwarsshop.transactions.presentation.model.PresentationTransaction

object PresentationTransactionMapper : BaseMapper<Transaction, PresentationTransaction>() {

    override fun transformTo(source: Transaction): PresentationTransaction = PresentationTransaction(
            value = source.value, date = source.date, lastFourDigitsCard = source.lastFourDigitsCard,
            name = source.name)

    override fun transformFrom(source: PresentationTransaction): Transaction {
        TODO("not implemented")
    }

}