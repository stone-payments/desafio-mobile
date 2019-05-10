package br.com.stone.vianna.starstore.view.card

import android.os.Bundle
import br.com.stone.vianna.starstore.R
import br.com.stone.vianna.starstore.baseClasses.BaseActivity
import br.com.stone.vianna.starstore.entity.PaymentRequest
import kotlinx.android.synthetic.main.activity_credit_card.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class CreditCardActivity : BaseActivity(), CardContract.View {

    val presenter: CardContract.Presenter by inject { parametersOf(this) }

    companion object {
        const val CHECKOUT_VALUE = "CHECKOUT_VALUE"
    }

    val value by lazy {
        intent.getIntExtra(CHECKOUT_VALUE, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_card)

        presenter.init(value)
        initializeViews()
    }

    private fun initializeViews() {

        cards_toolbar.title = "VOLTAR"
        cards_toolbar.setTitleTextColor(resources.getColor(android.R.color.white, theme))
        setSupportActionBar(cards_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        checkout_button.setOnClickListener {
            val paymentRequest = PaymentRequest()
            paymentRequest.cardNumber = et_card_number.rawText.toString()
            paymentRequest.cardHolder = et_card_holder_name.text.toString()
            paymentRequest.expirationDate = et_card_exp_date.text.toString()
            paymentRequest.securityCode = et_card_cvv.text.toString()

            presenter.onCheckoutButtonClicked(paymentRequest)
        }
    }

    override fun displayCardNumberError() {
        text_input_layout_card.error = getString(R.string.card_number_error)
    }

    override fun hideCardNumberError() {
        text_input_layout_card.error = null
    }

    override fun displayCardExpirationDateError() {
        text_input_layout_date.error = getString(R.string.expiration_date_error)
    }

    override fun hideCardExpirationDateError() {
        text_input_layout_date.error = null
    }

    override fun displayCardCvvError() {
        text_input_layout_cvv.error = getString(R.string.card_security_code_error)
    }

    override fun hideCardCvvError() {
        text_input_layout_cvv.error = null
    }


    override fun displayCardHolderError() {
        text_input_layout_card_holder_name.error = getString(R.string.card_holder_name_error)
    }

    override fun hideCardHolderError() {
        text_input_layout_card_holder_name.error = null
    }
}