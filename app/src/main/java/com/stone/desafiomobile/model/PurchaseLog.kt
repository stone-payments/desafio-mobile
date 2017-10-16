package com.stone.desafiomobile.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Entidade que guarda o historico de compras
 * @param id chave primaria da tabela
 * @param value valor da transação
 * @param date data da compra
 * @param lastCardDigits ultimos digitos do cartão
 * @param cardHolderName nome do portador do cartão
 */
@Entity(tableName = "purchase_logs")
data class PurchaseLog(
        @PrimaryKey var id: Long? = null,
        var value: Long? = null,
        var date: Date? = null,
        @ColumnInfo(name = "last_card_digits")
        var lastCardDigits: String? = null,
        @ColumnInfo(name = "card_holder_name")
        var cardHolderName: String? = null
) {

}