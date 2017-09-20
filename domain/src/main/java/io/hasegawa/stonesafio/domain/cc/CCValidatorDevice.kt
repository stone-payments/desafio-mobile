package io.hasegawa.stonesafio.domain.cc

interface CCValidatorDevice {
    fun isCardValid(card: CCModel): CCValidation
}