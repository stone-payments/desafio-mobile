package personal.pedrofigueiredo.milleniumstore.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import khttp.post
import kotlinx.android.synthetic.main.activity_checkout.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.longToast
import org.jetbrains.anko.uiThread
import personal.pedrofigueiredo.milleniumstore.R
import personal.pedrofigueiredo.milleniumstore.data.OrderDataPOJO

class CheckoutActivity : AppCompatActivity() {
    private val ORDER_API_URL = "https://private-c76649-milleniumstore.apiary-mock.com/orders"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val total: Int = intent.getIntExtra("CART_TOTAL", 0)

        btnSubmit.setOnClickListener {
            if(validateFields() && total > 0){
                val pd = indeterminateProgressDialog(getString(R.string.order_progress_msg), getString(R.string.order_progress_title))
                doAsync {
                    val gson = Gson()
                    val orderData = OrderDataPOJO(edtCardNumber.text.toString(),
                            total, edtCvv.text.toString(), edtCardHolder.text.toString(), edtExpDt.text.toString())

                    val dataToSend = gson.toJson(orderData)
                    val r = post(ORDER_API_URL, headers = mapOf("content-type" to "application/json"), data = dataToSend)

                    when(r.statusCode){
                        201 -> uiThread {
                            pd.dismiss()
                            finishOrder()
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

    private fun finishOrder(){
        longToast(getString(R.string.order_request_success))
        //TO-DO: clean cart, persist order and forward user back to home
    }

    private fun validateFields(): Boolean {
        if(edtCardHolder.text.isNullOrBlank()){
            edtCardHolder.error = getString(R.string.error_field_required)
            return false
        }
        if(edtCardNumber.text.isNullOrBlank()){
            edtCardNumber.error = getString(R.string.error_field_required)
            return false
        }
        if(edtCvv.text.isNullOrBlank()){
            edtCvv.error = getString(R.string.error_field_required)
            return false
        }
        if(edtExpDt.text.isNullOrBlank()){
            edtExpDt.error = getString(R.string.error_field_required)
            return false
        }
        return true
    }
}
