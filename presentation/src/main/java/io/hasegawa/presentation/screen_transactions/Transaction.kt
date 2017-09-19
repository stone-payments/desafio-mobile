package io.hasegawa.presentation.screen_transactions


data class Transaction(
        val instant: Long,
        val value: Long,
        val ccLast4Digits: String,
        val ccName: String)