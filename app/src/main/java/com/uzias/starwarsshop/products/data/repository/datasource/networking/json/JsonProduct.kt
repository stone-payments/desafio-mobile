package com.uzias.starwarsshop.products.data.repository.datasource.networking.json

import com.uzias.starwarsshop.core.domain.InvalidData

data class JsonProduct(var title: String = InvalidData.UNINITIALIZED.getString(),
                       var price: Double = InvalidData.UNINITIALIZED.getDouble(),
                       var seller: String = InvalidData.UNINITIALIZED.getString(),
                       var thumbnailHd: String = InvalidData.UNINITIALIZED.getString())