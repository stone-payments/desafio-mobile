package io.hasegawa.stonesafio.domain.listing

import java.io.Serializable


data class ListingItemModel(
        val id: String,
        val title: String,
        val price: Long,
        val zipcode: String,
        val seller: String,
        val thumbnailHd: String,
        val date: String) : Serializable