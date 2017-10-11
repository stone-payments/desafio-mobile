package com.stone.desafiomobile.utils

fun Long.formatPriceReal(): String {
    val stringValue = this.toString()
    val cents = stringValue.takeLast(2)
    var amount = stringValue.substring(0, stringValue.lastIndex - 1)

    if (amount == "") {
        amount = "0"
    }

    return "R$" + amount + "," + cents
}
