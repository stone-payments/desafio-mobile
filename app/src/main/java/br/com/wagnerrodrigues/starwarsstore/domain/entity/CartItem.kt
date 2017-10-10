package br.com.wagnerrodrigues.starwarsstore.domain.entity

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.math.BigDecimal

/**
 * Carrinho de compras
 * Simplifiquei o carrinho considerando que a aplicação não possui múltiplos usuários.
 */
@DatabaseTable(tableName = "cartItem")
class CartItem {

    @DatabaseField(generatedId = true)
    var id : Long? = null

    @DatabaseField
    var productName : String? = null

    @DatabaseField
    var productThumb : String? = null

    @DatabaseField
    var price : BigDecimal? = null

    @DatabaseField
    var quantity : Int? = null

    constructor(product: Product?, quantity: Int){
        this.productName = product?.title
        this.productThumb = product?.thumbnailHd
        this.price = product?.price?.multiply(BigDecimal(quantity))
        this.quantity = quantity
    }

    constructor(){

    }
}


