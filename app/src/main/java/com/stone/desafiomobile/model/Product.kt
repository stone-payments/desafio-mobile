package com.stone.desafiomobile.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import java.io.Serializable

@Entity(tableName = "products", primaryKeys = arrayOf("title", "seller"))
data class Product(
        var price: Long? = null,
        @ColumnInfo(name = "thumbnail_hd")
        var thumbnailHd: String? = null
) : Serializable {
    lateinit var title: String
    lateinit var seller: String

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
