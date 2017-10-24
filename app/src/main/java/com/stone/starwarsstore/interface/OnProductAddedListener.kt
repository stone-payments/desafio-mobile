package com.stone.starwarsstore.`interface`

import com.stone.starwarsstore.model.Product

interface OnProductAddedListener {
     fun onProductAddedToCart(product: Product?)
}