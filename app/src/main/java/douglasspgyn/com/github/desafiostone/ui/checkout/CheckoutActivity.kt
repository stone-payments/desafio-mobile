package douglasspgyn.com.github.desafiostone.ui.checkout

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import douglasspgyn.com.github.desafiostone.R
import douglasspgyn.com.github.desafiostone.common.extensions.*
import douglasspgyn.com.github.desafiostone.common.util.MaskEditTextChangedListener
import douglasspgyn.com.github.desafiostone.ui.main.MainActivity
import douglasspgyn.com.github.desafiostone.ui.order.OrderActivity
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : AppCompatActivity(), CheckoutContract.View {

    val presenter = CheckoutPresenter(this)
    private var orderPlaced = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setListeners()
    }

    override fun onResume() {
        super.onResume()

        if (orderPlaced) {
            goToMain()
        }

        presenter.calculateTotalProduct()
    }

    private fun setListeners() {
        val numberMask = MaskEditTextChangedListener("#### #### #### ####", cardNumber)
        cardNumber.addTextChangedListener(numberMask)

        val expiresDateMask = MaskEditTextChangedListener("##/##", cardExpiresDate)
        cardExpiresDate.addTextChangedListener(expiresDateMask)

        placeOrder.setOnClickListener {
            if (validFields()) {
                presenter.createOrder(cardNumber.text.toString(),
                        cardCvv.text.toString(),
                        cardHolderName.text.toString(),
                        cardExpiresDate.text.toString())
            }
        }
    }

    private fun validFields(): Boolean {
        when {
            cardNumber.text.toString().isEmpty() -> {
                cardNumber.error = getString(R.string.empty_fields)
                return false
            }
            !cardNumber.validCreditCardNumber() -> {
                cardNumber.error = getString(R.string.invalid_card_number)
                return false
            }
            cardHolderName.text.toString().isEmpty() -> {
                cardHolderName.error = getString(R.string.empty_fields)
                return false
            }
            cardExpiresDate.text.toString().isEmpty() -> {
                cardExpiresDate.error = getString(R.string.empty_fields)
                return false
            }
            !cardExpiresDate.validCreditCardExpiresDate() -> {
                cardExpiresDate.error = getString(R.string.invalid_card_expires_date)
                return false
            }
            cardCvv.text.toString().isEmpty() -> {
                cardCvv.error = getString(R.string.empty_fields)
                return false
            }
            else -> return true
        }
    }

    override fun updateTotalProduct(total: String) {
        checkoutTotal.text = getString(R.string.total_price, total)
    }

    override fun creatingOrder() {
        checkoutLoader.visible()
        loading.visible()
    }

    override fun orderCreated() {
        orderPlaced = true
        loading.gone()
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_order_placed, null)
        AlertDialog.Builder(this).create().apply {
            setView(view)
            setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.go_orders), { _, _ ->
                startActivity(Intent(this@CheckoutActivity, OrderActivity::class.java))
            })
            setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel), { _, _ ->
                goToMain()
            })
            show()
        }
    }

    override fun orderFailed() {
        checkoutLoader.gone()
        loading.gone()
        snackbar(getString(R.string.failed_place_order))
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (orderPlaced) {
            goToMain()
        } else {
            super.onBackPressed()
        }
    }
}
