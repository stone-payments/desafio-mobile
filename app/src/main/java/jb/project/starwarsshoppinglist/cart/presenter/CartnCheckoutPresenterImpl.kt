package jb.project.starwarsshoppinglist.cart.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmResults
import jb.project.starwarsshoppinglist.R
import jb.project.starwarsshoppinglist.cart.activity.view.CartnCheckoutView
import jb.project.starwarsshoppinglist.model.Cart
import jb.project.starwarsshoppinglist.model.Purchase
import jb.project.starwarsshoppinglist.service.ProductRepositoryProvider
import kotlin.properties.Delegates

class CartnCheckoutPresenterImpl : CartnCheckoutPresenter {
    lateinit var mView: CartnCheckoutView
    private var realm: Realm by Delegates.notNull()
    lateinit var cartItems: RealmResults<Cart>


    override fun init(cartnCheckoutView: CartnCheckoutView) {
        mView = cartnCheckoutView
        realm = Realm.getDefaultInstance()
        fetchData()
    }

    private fun fetchData() {
        realmSearchCart()
        if (cartItems.size > 0) {
            mView.setUpRecyclerView(cartItems)
        }
    }

    private fun realmSearchCart() {
        cartItems = realm.where(Cart::class.java).findAll()
    }

    override fun getCartValue(): Int {
        realmSearchCart()
        return cartItems.sumBy { it.quantity?.times(it.price!!) ?: 0 }
    }


    override fun sendCartAndSavePurchase(purchase: Purchase) {
        val repository = ProductRepositoryProvider.provideProductRepository()
        repository.initPurchase(purchase)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    if (result.isSuccessful)
                        realm.executeTransactionAsync({ bgRealm ->
                            Cart().deleteAll(bgRealm)
                            bgRealm.copyToRealm(result.body())

                        }, {
                            //all good
                            mView.orderPurchaseSuccessful()
                        }) {
                            //error on realm
                            mView.showToast(R.string.generic_error)
                        }
                }, {
                    //error on post
                    mView.showToast(R.string.generic_error)
                })
    }

    override fun validateCartItems(cardNumber: String, cardName: String, cardExp: String, CardCVV: String, totalCart: Int) = when {
        cardNumber == "" || cardNumber.length < 16 -> {
            mView.cardNumberError(R.string.invalid_card_number)
        }
        cardName == "" -> {
            mView.cardTextError(R.string.invalid_card_name)
        }
    //should validate date afterwards to check if is correct.
        cardExp == "" -> {
            mView.cardExpError(R.string.invalid_card_exp)
        }
        CardCVV == "" || CardCVV.length < 3 -> {
            mView.cardCvvError(R.string.invalid_card_cvv)
        }
        totalCart == 0 -> {
            mView.emptyCart(R.string.invalid_cart_number)
        }

    //all fields valid
        else -> {
            val purchase: Purchase = Purchase(
                    cardNumber,
                    totalCart,
                    CardCVV.toInt(),
                    cardName,
                    cardExp
            )

            sendCartAndSavePurchase(purchase)
        }
    }

    override fun changeRowCart(title: String, amount: Int) {
        realm.executeTransaction({ bgRealm ->
            Cart().updateAmount(bgRealm, title, amount)
        })

    }

    override fun deleteRowCart(title: String) {
        realm.executeTransaction({ bgRealm ->
            Cart().delete(bgRealm, title)
        })
    }


    override fun destroy() {
        realm.close()
    }
}