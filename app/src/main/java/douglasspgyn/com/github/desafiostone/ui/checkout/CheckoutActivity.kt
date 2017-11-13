package douglasspgyn.com.github.desafiostone.ui.checkout

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import douglasspgyn.com.github.desafiostone.R
import douglasspgyn.com.github.desafiostone.common.extensions.validCreditCardExpiresDate
import douglasspgyn.com.github.desafiostone.common.extensions.validCreditCardNumber
import douglasspgyn.com.github.desafiostone.common.util.MaskEditTextChangedListener
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : AppCompatActivity(), CheckoutContract.View {

    val presenter = CheckoutPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter.calculateTotalProduct()

        setListeners()
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

    }

    override fun orderCreated() {

    }

    override fun orderFailed() {

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
}
