package jb.project.starwarsshoppinglist.productDetail.presenter

import jb.project.starwarsshoppinglist.model.Cart
import jb.project.starwarsshoppinglist.productDetail.activity.view.ProductDetailView

/**
 * Created by Jb on 17/10/2017.
 */
interface ProductDetailPresenter {
    fun init(productDetailview: ProductDetailView)
    fun getCountCart(): String
    fun destroy()
    fun addCart(cart: Cart)
}