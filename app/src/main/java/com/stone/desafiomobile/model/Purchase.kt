package com.stone.desafiomobile.model

import com.google.gson.annotations.SerializedName

/**
 * Classe para guardar os dados da compra que serão enviados para a API
 * @param cardNumber numero do cartão
 * @param value valor da compra
 * @param cvv codigo de verificação do cartão
 * @param cardHolderName nome do portador do cartão
 * @param expDate data de validade do cartão
 */
data class Purchase(
        @SerializedName("card_number")
        val cardNumber: String,
        val value: Long,
        val cvv: Long,
        @SerializedName("card_holder_name")
        val cardHolderName: String,
        @SerializedName("exp_date")
        val expDate: String) {
}