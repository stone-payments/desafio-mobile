package jb.project.starwarsshoppinglist.cart.presenter

import jb.project.starwarsshoppinglist.cart.activity.view.CartnCheckoutView
import jb.project.starwarsshoppinglist.model.Purchase

/**
 * Created by Jb on 17/10/2017.
 */
interface CartnCheckoutPresenter {
    fun sendCartAndSavePurchase(purchase: Purchase) {}
    fun init(cartnCheckoutView: CartnCheckoutView) {}
    fun validateCartItems(cardNumber: String, cardName: String, cardExp: String, CardCVV: String, totalCart: Int)
    fun getCartValue(): Int
    fun destroy()
    fun deleteRowCart(title: String)
    fun changeRowCart(title: String, amount: Int)
    fun getCountCart(): String
}