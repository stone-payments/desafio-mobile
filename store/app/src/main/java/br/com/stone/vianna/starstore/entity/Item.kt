package br.com.stone.vianna.starstore.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Item {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var title: String = ""
    var price: Int = 0
    var seller: String = ""
    var thumbnailHd: String = ""
}