package com.stone.starwarsstore.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Order (
        @PrimaryKey var id: Long? = null,
        var lastFourDigits: String? = null,
        var value: Long? = 0,
        var cardHolderName: String? = null,
        var createdAt: Long? = 0) : RealmObject()