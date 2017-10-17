package com.stone.desafiomobile.view

import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import com.stone.desafiomobile.R
import com.stone.desafiomobile.di.DaggerInjectionComponent
import com.stone.desafiomobile.di.DatabaseModule
import com.stone.desafiomobile.di.RetrofitModule
import com.stone.desafiomobile.model.Purchase
import com.stone.desafiomobile.utils.formatPriceReal
import com.stone.desafiomobile.utils.validateDate
import com.stone.desafiomobile.utils.validateEmpty
import com.stone.desafiomobile.utils.validateMinLength
import com.stone.desafiomobile.viewmodel.CheckoutVm
import java.util.*

/**
 * Activity de finalização de compras
 */
class CheckoutActivity : BaseActivity() {
    companion object {
        val ARG_CART = "arg_cart"
    }

    // valor da compra
    internal var mCartItensValue: Long = 0

    // referencias do layout
    lateinit internal var mCardNumberET: EditText
    lateinit internal var mHolderNameET: EditText
    lateinit internal var mExpDateET: EditText
    lateinit internal var mCvvCodeET: EditText
    lateinit internal var mValueView: TextView
    lateinit internal var mBuyButton: Button
    lateinit internal var mProgressBar: FrameLayout


    lateinit internal var mViewModel: CheckoutVm


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // recupera o valor da compra
        mCartItensValue = intent.getLongExtra(ARG_CART, 0)

        setWidgets()

        mViewModel = ViewModelProviders.of(this).get(CheckoutVm::class.java)


        //injeta as dependencias
        val injectionComponent = DaggerInjectionComponent.builder()
                .retrofitModule(RetrofitModule())
                .databaseModule(DatabaseModule(this))
                .build()
        injectionComponent.inject(mViewModel)

    }

    /**
     * Pega as referências do layout
     */
    fun setWidgets() {
        mCardNumberET = findViewById(R.id.card_number)
        mHolderNameET = findViewById(R.id.card_holder_name)
        mExpDateET = findViewById(R.id.exp_date)
        mCvvCodeET = findViewById(R.id.cvv)
        mValueView = findViewById(R.id.purchase_value)
        mValueView.text = mCartItensValue.formatPriceReal()

        mBuyButton = findViewById(R.id.buy_button)
        mBuyButton.setOnClickListener { completePurchase() }

        mProgressBar = findViewById(R.id.progress_bar)
    }

    /**
     * Realiza a compra
     */
    fun completePurchase() {

        // se o formulario estiver invalido para imediatamente
        if (!isFormValid()) {
            return
        }

        // cria um objeto da compra
        val purchase = Purchase(
                mCardNumberET.text.toString(),
                mCartItensValue,
                mCvvCodeET.text.toString().toLong(),
                mHolderNameET.text.toString(),
                mExpDateET.text.toString()
        )

        // mostra o loading
        mProgressBar.visibility = View.VISIBLE

        // faz a requisição
        mViewModel.buyProducts(purchase, { result ->
            // mostra o resultado em um alerta
            showAlert(result)
            // esconde o loading
            mProgressBar.visibility = View.GONE
        })

    }

    /**
     * valida o formulario
     */
    fun isFormValid(): Boolean {
        val validList = ArrayList<EditText?>()

        // valida cada formulario
        validList.add(mCardNumberET.validateEmpty()?.validateMinLength(14))
        validList.add(mHolderNameET.validateEmpty())
        validList.add(mExpDateET.validateEmpty()?.validateDate())
        validList.add(mCvvCodeET.validateEmpty()?.validateMinLength(3))

        // percorre os inputs
        // se algum deles for nulo significa que o fomulario esta invalido
        validList.forEach { valid -> if (valid == null) return false }

        return true;
    }

    /**
     * Mostra um alerta
     * @param message id da string para mostrar
     */
    fun showAlert(message: Int) {
        val dialog = AlertDialog.Builder(this)
                .setMessage(getString(message))
                .setPositiveButton(R.string.ok, DialogInterface.OnClickListener { dialogInterface: DialogInterface, _ ->
                    dialogInterface.dismiss()
                })
                // se o dialogo for dispensado fecha a atividade
                .setOnDismissListener { dialogInterface: DialogInterface ->
                    if (message == R.string.purchase_success) {
                        finish()
                    }
                }
                .create()
        dialog.show()
    }

}
