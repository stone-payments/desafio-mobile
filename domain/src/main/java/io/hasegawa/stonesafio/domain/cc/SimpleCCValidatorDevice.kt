package io.hasegawa.stonesafio.domain.cc

class SimpleCCValidatorDevice : CCValidatorDevice {
    private val numberRegex by lazy { Regex("^[0-9]{16}$") }

    override fun isCardValid(card: CCModel): CCValidation =
            CCValidation(
                    number = card.number.matches(numberRegex),
                    name = card.name.isNotBlank(),
                    cvv = card.cvv in 100..999,
                    expDateMonth = card.expDateMonth in 1..12,
                    expDateYear = card.expDateYear in 0..99)
}