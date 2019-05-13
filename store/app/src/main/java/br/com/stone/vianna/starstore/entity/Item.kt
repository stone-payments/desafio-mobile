package br.com.stone.vianna.starstore.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Item(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val title: String,
        val price: Int,
        val seller: String,
        val thumbnailHd: String)