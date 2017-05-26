package com.partiufast.mercadodoimperador.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.partiufast.mercadodoimperador.R
import android.widget.DatePicker
import android.app.DatePickerDialog
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_payment.*
import org.jetbrains.anko.find
import java.util.*
import java.text.SimpleDateFormat
import android.view.View.OnFocusChangeListener
import android.view.WindowManager
import android.text.InputType
import android.os.Build
import android.support.v7.widget.Toolbar
import android.view.MenuItem


class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        if (supportActionBar != null)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val myCalendar = Calendar.getInstance()

        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(myCalendar, input_date_edittext)
        }
        disableSoftInputFromAppearing(input_date_edittext)

        input_date_edittext.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                DatePickerDialog(this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show()
            } else {
                // Hide your calender here
            }
        }


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

        val myFormat = "MM/dd/yy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        editText.setText(sdf.format(calendar.getTime()))
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
