package br.com.stone.vianna.starstore.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Item {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    @ColumnInfo(name = "title")
    var title: String = ""
    @ColumnInfo(name = "price")
    var price: Int = 0
    @ColumnInfo(name = "seller")
    var seller: String = ""
    @ColumnInfo(name = "thumbnailHd")
    var thumbnailHd: String = ""
}