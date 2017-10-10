package br.com.wagnerrodrigues.starwarsstore.presentation.event

import br.com.wagnerrodrigues.starwarsstore.domain.entity.CartItem


class ButtonRemoveFromCartPressedEvent(val cartItem: CartItem)