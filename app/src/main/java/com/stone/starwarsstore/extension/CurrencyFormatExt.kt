package com.stone.starwarsstore.extension

fun Long.formatPrice(): String {
    val stringValue = this.toString()
    val rest = stringValue.takeLast(2)

    if (stringValue.length > 1)
    return "R$ " + stringValue.substring(0, stringValue.lastIndex - 1) + "," + rest else return "R$ 0,00"
}