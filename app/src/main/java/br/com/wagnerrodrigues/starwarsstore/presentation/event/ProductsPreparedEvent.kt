package br.com.wagnerrodrigues.starwarsstore.presentation.event

import br.com.wagnerrodrigues.starwarsstore.domain.entity.Product

class ProductsPreparedEvent(val products: List<Product>?)