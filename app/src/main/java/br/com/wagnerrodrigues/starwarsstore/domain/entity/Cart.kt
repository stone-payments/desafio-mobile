package br.com.wagnerrodrigues.starwarsstore.domain.entity

import java.math.BigDecimal

class Cart {

    var cartItems : List<CartItem>? = null

    var totalPurchase : BigDecimal = BigDecimal(0)

}