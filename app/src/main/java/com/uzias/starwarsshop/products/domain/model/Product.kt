package com.uzias.starwarsshop.products.domain.model

import com.uzias.starwarsshop.core.domain.InvalidData

data class Product(val title: String = InvalidData.UNINITIALIZED.getString(),
                   val pictureUrl: String = InvalidData.UNINITIALIZED.getString(),
                   val price: Double = InvalidData.UNINITIALIZED.getDouble(),
                   val seller: String = InvalidData.UNINITIALIZED.getString())