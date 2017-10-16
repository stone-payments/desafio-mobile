package personal.pedrofigueiredo.milleniumstore.activities

import android.content.Intent
import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.Gson
import khttp.post
import kotlinx.android.synthetic.main.activity_checkout.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.longToast
import org.jetbrains.anko.uiThread
import personal.pedrofigueiredo.milleniumstore.R
import personal.pedrofigueiredo.milleniumstore.common.GlobalApplication
import personal.pedrofigueiredo.milleniumstore.components.MyEditTextDatePicker
import personal.pedrofigueiredo.milleniumstore.data.Order
import personal.pedrofigueiredo.milleniumstore.data.OrderDataPOJO
import java.util.*

class CheckoutActivity : AppCompatActivity() {
    private val ORDER_API_URL = "https://private-c76649-milleniumstore.apiary-mock.com/orders"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val total: Int = intent.getIntExtra("CART_TOTAL", 0)

        // creates datepicker on the expiration date edit text
        MyEditTextDatePicker(_context = this, editTextViewID = R.id.edtExpDt)

        btnSubmit.setOnClickListener {
            if (validateFields() && total > 0) {
                val pd = indeterminateProgressDialog(getString(R.string.order_progress_msg), getString(R.string.order_progress_title))
                doAsync {
                    val gson = Gson()
                    val orderData = OrderDataPOJO(edtCardNumber.text.toString(),
                            total, edtCvv.text.toString(), edtCardHolder.text.toString(), edtExpDt.text.toString())

                    val dataToSend = gson.toJson(orderData)
                    val r = post(ORDER_API_URL, headers = mapOf("content-type" to "application/json"), data = dataToSend)

                    when (r.statusCode) {
                        201 -> {
                            try {
                                val finished = finishOrder(orderData)
                                if(finished){
                                    uiThread {
                                        pd.dismiss()
                                        // notify user
                                        longToast(getString(R.string.order_request_success))
                                        startActivity(Intent(this@CheckoutActivity, OrderListActivity::class.java))
                                    }
                                } else {
                                    pd.dismiss()
                                    longToast(getString(R.string.order_request_error))
                                }

                            } catch (e: SQLiteException) {
                                Log.e("SQLITE_FINISH_ORDER", e.message)
                            }

                        }
                        else -> {
                            pd.dismiss()
                            longToast(getString(R.string.order_request_error))
                        }
                    }


                }
            }
        }

    }

    /**
     * Stores the transaction on SQLite database and cleans the shopping cart before returning control to user
     * @param data object holding the required data to be persisted
     * @return True if everything went ok, false if any exception was raised
     */
    private fun finishOrder(data: OrderDataPOJO) : Boolean {
        val app = application as GlobalApplication

        val db = app.getDB()
        val orderDAO = db?.orderDao()

        // ignoring the API return since it's just a mock at this time, and getting the current time manually
        val dataToInsert = Order(
                value = data.value,
                lastFourDigits = data.card_number.substring(data.card_number.lastIndex - 3),
                cardHolder = data.card_holder_name,
                dtProcessed = GregorianCalendar.getInstance().time
        )
        return try {
            // insert record on DB
            orderDAO?.insertOrder(dataToInsert)

            // clean cart
            val cart = app.getShoppingCart()
            cart?.clear()

            true
        } catch (dbException : SQLiteException){
            Log.e("SQLITE_INSERT_ERROR", dbException.message)
            false
        }


    }

    /**
     * Simple validation for the fields:
     * Currently just checks if all required fields were filled.
     * No other constraints like min/max size are enforced besides type input specified in the edittext element
     */
    private fun validateFields(): Boolean {
        if (edtCardHolder.text.isNullOrBlank()) {
            edtCardHolder.error = getString(R.string.error_field_required)
            return false
        }
        if (edtCardNumber.text.isNullOrBlank()) {
            edtCardNumber.error = getString(R.string.error_field_required)
            return false
        }
        if (edtCvv.text.isNullOrBlank()) {
            edtCvv.error = getString(R.string.error_field_required)
            return false
        }
        if (edtExpDt.text.isNullOrBlank()) {
            edtExpDt.error = getString(R.string.error_field_required)
            return false
        }
        return true
    }
}
