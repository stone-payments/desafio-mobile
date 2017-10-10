package br.com.wagnerrodrigues.starwarsstore.domain.event

import br.com.wagnerrodrigues.starwarsstore.domain.entity.Product

class ProductsFetchedEvent(val products : List<Product>?)