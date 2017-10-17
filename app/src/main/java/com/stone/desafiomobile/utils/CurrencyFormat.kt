package com.stone.desafiomobile.utils

/**
 * Metodo para formatar moedas
 * @return moeda formatada para Real
 */
fun Long.formatPriceReal(): String {
    val stringValue = this.toString()
    // Ultimos dois digitos como centavos
    val cents = stringValue.takeLast(2)
    // resto dos digitos
    var amount = stringValue.substring(0, stringValue.lastIndex - 1)

    // impede que fique mal formatado quando o valor for somente centavos ex: R$,50
    if (amount == "") {
        amount = "0"
    }

    return "R$" + amount + "," + cents
}
