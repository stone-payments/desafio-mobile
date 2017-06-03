package com.partiufast.mercadodoimperador.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.partiufast.mercadodoimperador.R
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_payment.*
import java.util.*
import java.text.SimpleDateFormat
import android.text.InputType
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.partiufast.mercadodoimperador.CartPostRequest
import com.partiufast.mercadodoimperador.ui.custom.MonthYearPickerDialog
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import android.app.ProgressDialog
import org.jetbrains.anko.alert


class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        val total_price = intent.getStringExtra(getString(R.string.CART_VALUE_EXTRA))

        total_price_text_view.text = "Valor Total: R$$total_price"

        if (supportActionBar != null)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)

       val myCalendar = Calendar.getInstance()
        val pickerDialog = MonthYearPickerDialog()
        pickerDialog.setListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            updateLabel(myCalendar, input_date_edittext)
        }
        disableSoftInputFromAppearing(input_date_edittext)
        input_date_edittext.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                pickerDialog.show(fragmentManager, "MonthYearPickerDialog")
                input_date_edittext.error = null
            }
        }

        attempt_payment_button.setOnClickListener {
            attemptPayment(myCalendar, total_price)
        }
        //TODO: adicionar valor total na view de forma do pagamento
    }

    private fun attemptPayment(calendar: Calendar, total_price: String) {
        input_layout_fullname.error = null
        input_layout_number.error = null
        input_verifier_layout.error = null
        input_date_layout.error = null

        val fullname = input_fullname_edittext.text.toString()
        val cardnumber = input_credit_card_number_edittext.text.toString()
        val verificationNumber = input_verifiernumber_edittext.text.toString()
        val date = input_date_edittext.text.toString()

        var cancel = false

        //check for a valid name
        if (TextUtils.isEmpty(date) || calendar.timeInMillis < System.currentTimeMillis()){
            input_date_edittext.error = getString(R.string.error_invalid_date)
            cancel = true
        }

        if (TextUtils.isEmpty(fullname) || !isFullnameValid(fullname)) {
            input_layout_fullname.error = getString(R.string.error_invalid_fullname)
            cancel = true

        }

        if (TextUtils.isEmpty(cardnumber) || !isCreditCardNumberValid(cardnumber)) {
            input_credit_card_number_edittext.error = getString(R.string.error_invalid_credit_card_number)
            cancel = true

        }
        if (TextUtils.isEmpty(verificationNumber) || !isVerificationNumberValid(verificationNumber)){
            input_verifiernumber_edittext.error = getString(R.string.error_invalid_verifier)
            cancel = true

        }
        if (!cancel){
            val dialog = ProgressDialog.show(this, "",
                    "Carregando...", true)
            doAsync {
                val request = CartPostRequest()
                val response = request.run(cardnumber, total_price.replace(".", "").toInt(), fullname, verificationNumber.toInt(), date)
                uiThread {
                    dialog.dismiss()
                    if (response == 200){
                        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                        val currentDateandTime = sdf.format(Date())
                        alert("R$$total_price às $currentDateandTime\n"+fullname+"\nCartão x"+cardnumber.substring(12), "Compra Efetuada") {
                            positiveButton("Ok") {
                                //processAnOrder()
                            }
                        }.show()

                    }
                    else {
                        Toast.makeText(applicationContext, "Compra não efetuada :(", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }



    }

    private fun isVerificationNumberValid(verificationNumber: String): Boolean {
        return (verificationNumber.length == 3)
    }

    private fun  isCreditCardNumberValid(cardnumber: String): Boolean {
        return (cardnumber.length == 16)
    }


    private fun isFullnameValid(fullname: String): Boolean {
        return (fullname.contains(' ') && fullname.length > 8)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
        // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateLabel(calendar: Calendar, editText: EditText) {

        val myFormat = "MM/yy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        editText.setText(sdf.format(calendar.time))
    }

    fun disableSoftInputFromAppearing(editText: EditText) {
        if (Build.VERSION.SDK_INT >= 11) {
            editText.setRawInputType(InputType.TYPE_CLASS_TEXT)
            editText.setTextIsSelectable(true)
        } else {
            editText.setRawInputType(InputType.TYPE_NULL)
            editText.isFocusable = true
        }
    }
}
