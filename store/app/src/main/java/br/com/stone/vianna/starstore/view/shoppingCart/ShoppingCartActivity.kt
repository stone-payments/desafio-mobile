package br.com.stone.vianna.starstore.view.shoppingCart

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import br.com.stone.vianna.starstore.R
import br.com.stone.vianna.starstore.baseClasses.BaseActivity
import br.com.stone.vianna.starstore.entity.Item
import br.com.stone.vianna.starstore.extensions.toMoneyFormat
import br.com.stone.vianna.starstore.view.card.CreditCardActivity
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ShoppingCartActivity : BaseActivity(), ShoppingCartContract.View {

    private val presenter: ShoppingCartContract.Presenter by inject { parametersOf(this) }
    lateinit var adapter: ShoppingCartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)

        initializeViews()
        presenter.init()
    }

    private fun initializeViews() {

        cart_toolbar.title = getString(R.string.shopping_cart_screen_title)
        setSupportActionBar(cart_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this)
        cart_list.layoutManager = layoutManager

        proceed_to_checkout_button.setOnClickListener {
            presenter.onProceedToCheckoutButtonClicked()
        }
    }

    override fun updateCartItems(items: List<Item>) {
        adapter = ShoppingCartAdapter(items) {
            presenter.removeItem(it)
        }
        cart_list.adapter = adapter
    }

    override fun setTotalValue(totalValue: Int) {
        tv_total_value?.text = totalValue.toMoneyFormat()
    }

    override fun openCheckout(value: Int) {
        val intent = Intent(this, CreditCardActivity::class.java).apply {
            putExtra(CreditCardActivity.CHECKOUT_VALUE, value)
        }
        startActivity(intent)
        finish()
    }
}