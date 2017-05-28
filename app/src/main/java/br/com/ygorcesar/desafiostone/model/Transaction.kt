package br.com.ygorcesar.desafiostone.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Transaction(@PrimaryKey var id: Int = 0, var value: Double = 0.0, var date: String = "",
                       var ccLastDigits: String = "", var card_holder_name: String = "") : RealmObject()