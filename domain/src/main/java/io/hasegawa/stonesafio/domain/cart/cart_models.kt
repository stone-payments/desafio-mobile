package io.hasegawa.stonesafio.domain.cart

import java.io.Serializable

data class CartProduct(
        val id: String,
        val title: String,
        val thumbnailUrl: String,
        val price: Long,
        val instantAdded: Long) : Serializable