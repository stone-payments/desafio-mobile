package com.stone.desafiomobile.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import java.io.Serializable

/**
 * Entidade que guarda os produtos da loja
 * @param price preço do produto
 * @param thumbnailHd foto do produto
 */
@Entity(tableName = "products", primaryKeys = arrayOf("title", "seller"))
data class Product(
        var price: Long? = null,
        @ColumnInfo(name = "thumbnail_hd")
        var thumbnailHd: String? = null
) : Serializable {
    lateinit var title: String
    lateinit var seller: String

    /**
     * construtor secundario para impedir que a chave primaria (title e seller) seja nula
     * @param title titulo do produto
     * @param seller vendedor do produto
     */
    constructor(
            title: String,
            price: Long? = null,
            seller: String,
            thumbnailHd: String? = null
    ) : this(price, thumbnailHd) {
        this.title = title
        this.seller = seller
    }
}
