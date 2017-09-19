package io.hasegawa.presentation.screen_cart


data class CartPaymentResult(
        val success: Boolean,
        val value: Long,
        val instant: Long)