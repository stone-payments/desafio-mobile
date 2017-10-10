package br.com.wagnerrodrigues.starwarsstore.presentation.event

import br.com.wagnerrodrigues.starwarsstore.domain.entity.Product

class ProductAddedToCartEvent(val product: Product?, val quantity: Int)