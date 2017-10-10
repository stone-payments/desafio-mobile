package personal.pedrofigueiredo.milleniumstore.common

import android.app.Application
import personal.pedrofigueiredo.milleniumstore.data.ShoppingCart

class GlobalApplication() : Application() {
    private var sCart: ShoppingCart? = null

    override fun onCreate() {
        super.onCreate()
        sCart = ShoppingCart()
    }

    fun getShoppingCart() : ShoppingCart?{
        return sCart
    }
}