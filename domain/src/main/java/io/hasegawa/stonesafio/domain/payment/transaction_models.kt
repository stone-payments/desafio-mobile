package io.hasegawa.stonesafio.domain.payment

data class TransactionModel(
        val id: String,
        val value: Long,
        val instant: Long,
        val ccLast4Digits: String,
        val ccName: String)
