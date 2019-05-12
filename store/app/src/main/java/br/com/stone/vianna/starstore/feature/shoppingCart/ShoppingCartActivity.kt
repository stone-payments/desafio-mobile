package br.com.stone.vianna.starstore.feature.shoppingCart

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import br.com.stone.vianna.starstore.R
import br.com.stone.vianna.starstore.baseClasses.BaseActivity
import br.com.stone.vianna.starstore.entity.Item
import br.com.stone.vianna.starstore.helper.toMoneyFormat
import br.com.stone.vianna.starstore.feature.card.CreditCardActivity
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ShoppingCartActivity : BaseActivity(), ShoppingCartContract.View {

    private val presenter: ShoppingCartContract.Presenter by inject { parametersOf(this) }
    private val adapter: ShoppingCartAdapter = ShoppingCartAdapter {
        presenter.removeItem(it)
    }

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

        cart_list.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        cart_list.layoutManager = layoutManager

        proceed_to_checkout_button.setOnClickListener {
            presenter.onProceedToCheckoutButtonClicked()
        }
    }

    override fun updateCartItems(items: List<Item>) {
        adapter.updateItems(items)
    }

    override fun setTotalValue(totalValue: String) {
        tv_total_value?.text = totalValue
    }

    override fun openCheckout(value: Int) {
        val intent = Intent(this, CreditCardActivity::class.java).apply {
            putExtra(CreditCardActivity.CHECKOUT_VALUE, value)
        }
        startActivity(intent)
        finish()
    }

    override fun onStop() {
        super.onStop()
        presenter.clearEvents()
    }
}