package com.uzias.starwarsshop.products.presentation.model

import com.uzias.starwarsshop.core.domain.InvalidData
import java.text.NumberFormat

data class PresentationProduct(val title: String = InvalidData.UNINITIALIZED.getString(),
                               val pictureUrl: String = InvalidData.UNINITIALIZED.getString(),
                               val price: Double = InvalidData.UNINITIALIZED.getDouble(),
                               val seller: String = InvalidData.UNINITIALIZED.getString(),
                               var totalItem: Int = 0) {

    fun getPriceFormatted() : String {
        return NumberFormat.getCurrencyInstance().format(price)
    }
}