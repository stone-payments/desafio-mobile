package com.uzias.starwarsshop.transactions.data.datasource.database.mapper

import com.uzias.starwarsshop.core.mapper.BaseMapper
import com.uzias.starwarsshop.transactions.data.datasource.database.model.RealmTransaction
import com.uzias.starwarsshop.transactions.domain.model.Transaction

object RealmTransactionMapper : BaseMapper<RealmTransaction, Transaction>() {

    override fun transformFrom(source: Transaction): RealmTransaction =
            RealmTransaction(value = source.value, date = source.date,
                lastFourDigitsCard =  source.lastFourDigitsCard, name = source.name)

    override fun transformTo(source: RealmTransaction): Transaction =
            Transaction(value = source.value, date = source.date,
            lastFourDigitsCard =  source.lastFourDigitsCard, name = source.name)

}