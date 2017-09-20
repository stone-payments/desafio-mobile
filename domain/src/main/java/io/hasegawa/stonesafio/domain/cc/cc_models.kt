package io.hasegawa.stonesafio.domain.cc


data class CCModel(val number: String,
                   val name: String,
                   val cvv: Int,
                   val expDateMonth: Int,
                   val expDateYear: Int)

data class CCValidation(
        val number: Boolean,
        val name: Boolean,
        val cvv: Boolean,
        val expDateMonth: Boolean,
        val expDateYear: Boolean) {
    val expDate = expDateMonth && expDateYear
}