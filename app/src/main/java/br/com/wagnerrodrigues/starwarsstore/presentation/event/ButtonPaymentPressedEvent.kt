package br.com.wagnerrodrigues.starwarsstore.presentation.event

import br.com.wagnerrodrigues.starwarsstore.domain.entity.Product

class ButtonPaymentPressedEvent(val product: Product?, val quantity: Int)