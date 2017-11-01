package com.uzias.starwarsshop.transactions.data.datasource.database.model

import com.uzias.starwarsshop.core.domain.InvalidData
import io.realm.RealmObject
import java.util.*

open class RealmTransaction(
        var name: String = InvalidData.UNINITIALIZED.getString(),
        var value: Double = InvalidData.UNINITIALIZED.getDouble(),
        var lastFourDigitsCard: String = InvalidData.UNINITIALIZED.getString(),
        var date: Date = InvalidData.UNINITIALIZED.getDate()
) : RealmObject()