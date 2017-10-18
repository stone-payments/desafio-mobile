package jb.project.starwarsshoppinglist.cart.activity.view

import io.realm.RealmResults
import jb.project.starwarsshoppinglist.model.Cart


/**
 * Created by Jb on 17/10/2017.
 */
interface CartnCheckoutView {
  
    fun cardNumberError(msgId: Int)
    fun cardTextError(msgId: Int)
    fun cardExpError(msgId: Int)
    fun cardCvvError(msgId: Int)
    fun emptyCart(msgId: Int)
    fun orderPurchaseSuccessful()
    fun showToast(msgId: Int)
    fun setUpRecyclerView(cartItems: RealmResults<Cart>)
}