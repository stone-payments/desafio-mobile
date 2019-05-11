package br.com.stone.vianna.starstore.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Item(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val title: String,
        val price: Int,
        val seller: String,
        val thumbnailHd: String)