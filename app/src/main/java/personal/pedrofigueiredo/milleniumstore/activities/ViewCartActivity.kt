package personal.pedrofigueiredo.milleniumstore.activities

import android.content.Intent
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
        val totalCartValue = cart?.getTotalPrice()

        val cartAdapter = CartListAdapter(this, cart)
        cartList.adapter = cartAdapter
        cartAdapter.notifyDataSetChanged()

        cartTotal.text = "Total: $totalCartValue"

        btnClear.setOnClickListener {
            clearCart(cart)
        }

        btnCheckout.setOnClickListener {
            goToCheckout(totalCartValue)
        }
    }

    private fun clearCart(c: ShoppingCart?){
        c?.clear()
        this.recreate()
    }

    private fun goToCheckout(total: Int?){
        val intent = Intent(this, CheckoutActivity::class.java)
        intent.putExtra("CART_TOTAL", total)
        startActivity(intent)
    }
}
