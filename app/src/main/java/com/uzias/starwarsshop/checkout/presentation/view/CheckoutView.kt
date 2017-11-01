package com.uzias.starwarsshop.checkout.presentation.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.view.View
import com.cooltechworks.creditcarddesign.CreditCardUtils
import com.uzias.starwarsshop.R
import com.uzias.starwarsshop.checkout.presentation.model.PresentationCreditCard
import com.uzias.starwarsshop.checkout.presentation.presenter.CheckoutPresenter
import com.uzias.starwarsshop.core.presentation.BaseActivity
import com.uzias.starwarsshop.core.presentation.BasePresenter
import com.uzias.starwarsshop.core.presentation.BaseView
import com.uzias.starwarsshop.util.changeTextWithAnimation
import dagger.android.AndroidInjection
import io.reactivex.functions.Action
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.NumberFormat
import javax.inject.Inject

interface CheckoutView {

    fun changeTextButtonCheckoutToConfirm()
    fun setCreditCard(presentationCreditCard: PresentationCreditCard)
    fun setTotalPrice(totalPrice: Double)
    fun finishActivityWithResultOk()
}

class CheckoutActivity : BaseActivity(), CheckoutView {

    companion object {
        val EXTRA_TOTAL_PRICE = "total_price"
        val CHECKOUT_REQUEST_CODE = 100
        val ADD_CREDIT_CARD_REQUEST_CODE = 101

        fun newInstance(context: Context, totalPrice: Double): Intent {
            val intent = Intent(context, CheckoutActivity::class.java)
            intent.putExtra(EXTRA_TOTAL_PRICE, totalPrice)
            return intent
        }
    }

    @Inject
    lateinit var presenter: CheckoutPresenter

    override fun getPresenter(): BasePresenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        setSupportActionBar(toolbar)
        setupView()
        injectDependencies()
    }

    private fun setupView() {
        supportActionBar?.let {
            it.title = getString(R.string.label_checkout)
            it.setDisplayHomeAsUpEnabled(true)
        }

        buttonCheckout.setOnClickListener { presenter.clickedButtonCheckout() }
    }

    override fun injectDependencies() {
        AndroidInjection.inject(this)
    }


    override fun changeTextButtonCheckoutToConfirm() {
        buttonCheckout.changeTextWithAnimation(getString(R.string.label_confirm))
    }

    override fun setCreditCard(presentationCreditCard: PresentationCreditCard) {
        crediCardView.cardHolderName = presentationCreditCard.name
        crediCardView.cardNumber = presentationCreditCard.cardNumber
        crediCardView.setCardExpiry(presentationCreditCard.expiry)
        crediCardView.cvv = presentationCreditCard.cvv
    }

    override fun finishActivityWithResultOk() {
        setResult(Activity.RESULT_OK)
        finish()
    }
    override fun setTotalPrice(totalPrice: Double) {
        buttonTotal.text = getString(R.string.label_total_shop,
                NumberFormat.getCurrencyInstance().format(totalPrice))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == ADD_CREDIT_CARD_REQUEST_CODE) {
            formatResultToCreditCard(data)
        }
    }

    private fun formatResultToCreditCard(data: Intent?) {
        data?.let {
            val cardHolderName = it.getStringExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME)
            val cardNumber = it.getStringExtra(CreditCardUtils.EXTRA_CARD_NUMBER)
            val expiry = it.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY)
            val cvv = it.getStringExtra(CreditCardUtils.EXTRA_CARD_CVV)
            if (cardHolderName != null && cardNumber != null && expiry != null && cvv != null){
                val creditCard = PresentationCreditCard(name = cardHolderName, cardNumber = cardNumber,
                        expiry = expiry, cvv = cvv)
                presenter.resultCreditCard(creditCard)
            }
        }
    }

    override fun showLoading(): Action = Action {
        progressBar.visibility = View.VISIBLE
        buttonCheckout.isClickable = false
    }

    override fun hideLoading(): Action = Action {
        progressBar.visibility = View.GONE
        buttonCheckout.isClickable = true
    }

    override fun networkingErrorState(): Action = Action { showDialogTryAgain(R.string.label_network_error) }

    override fun showErrorState(): Action = Action { showDialogTryAgain(R.string.label_generic_error) }

    override fun hideErrorState(): Action = Action { }

    private fun showDialogTryAgain(@StringRes message: Int){
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setPositiveButton(R.string.label_try_again_dialog, { _,_ ->
            presenter.clickedButtonTryAgain() } )
        builder.setNegativeButton(R.string.label_cancel) { dialog, _ -> dialog.cancel() }
        builder.show()
    }

}