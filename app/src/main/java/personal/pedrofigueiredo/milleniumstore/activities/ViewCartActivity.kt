package personal.pedrofigueiredo.milleniumstore.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_view_cart.*
import personal.pedrofigueiredo.milleniumstore.R
import personal.pedrofigueiredo.milleniumstore.adapters.CartListAdapter
import personal.pedrofigueiredo.milleniumstore.common.GlobalApplication
import personal.pedrofigueiredo.milleniumstore.data.ShoppingCart

class ViewCartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_cart)

        val app = application as GlobalApplication
        val cart: ShoppingCart? = app.getShoppingCart()

        val cartAdapter = CartListAdapter(this, cart)
        cartList.adapter = cartAdapter
        cartAdapter.notifyDataSetChanged()

        cartTotal.text = "Total: ${cart?.getTotalPrice()}"

        btnClear.setOnClickListener {
            clearCart(cart)
        }
    }

    private fun clearCart(c: ShoppingCart?){
        c?.clear()
        this.recreate()
    }
}
