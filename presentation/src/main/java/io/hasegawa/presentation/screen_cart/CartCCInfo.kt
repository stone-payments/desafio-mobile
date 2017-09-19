package io.hasegawa.presentation.screen_cart

data class CartCCInfo(
        val number: String = "",
        val numberValid: Boolean = false,
        val name: String = "",
        val nameValid: Boolean = false,
        val cvv: String = "",
        val cvvValid: Boolean = false,
        val expDateMonth: String = "",
        val expDateMonthValid: Boolean = false,
        val expDateYear: String = "",
        val expDateYearValid: Boolean = false) {

    val valid = numberValid && nameValid && cvvValid && expDateMonthValid && expDateYearValid
}