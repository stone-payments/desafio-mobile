package io.hasegawa.stonesafio.domain.payment


data class PaymentRequest(
        val ccNumber: String,
        val ccName: String,
        val ccCVV: Int,
        val ccExpDate: String,
        val value: Long)

data class PaymentResult(
        val success: Boolean,
        val value: Long,
        val instant: Long,
        val ccLast4Digits: String,
        val ccName: String)