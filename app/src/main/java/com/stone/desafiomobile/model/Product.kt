package com.stone.desafiomobile.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName="products")
data class Product(
        @PrimaryKey
        var id: Long? = null,
        var title: String? = null,
        var price: Float? = null,
        var seller: String? = null,
        @ColumnInfo(name = "thumbnail_hd")
        var thumbnailHd: String? = null
)
