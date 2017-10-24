package com.stone.starwarsstore.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.stone.starwarsstore.Cart
import com.stone.starwarsstore.Mask
import com.stone.starwarsstore.R
import com.stone.starwarsstore.extension.formatPrice
import com.stone.starwarsstore.model.Order
import com.stone.starwarsstore.model.Transaction
import com.stone.starwarsstore.service.RestService
import kotlinx.android.synthetic.main.activity_finish_order.*
import io.realm.Realm
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.toast

class FinishOrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_order)

        setTitle(getString(R.string.title_payment))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        actFinishOrder_etCardExpire.addTextChangedListener(Mask.insert(actFinishOrder_etCardExpire))

        actFinishOrder_tvTotalPrice.text = Cart.getTotalPrice()?.formatPrice()

        actFinishOrder_btFinish.setOnClickListener {
            if (validateFields()) {
            val transaction = Transaction(actFinishOrder_etCardNumber.text.toString(),
                    Cart.getTotalPrice(),
                    actFinishOrder_etCardCode.text.toString().toLong(),
                    actFinishOrder_etCardHolder.text.toString(),
                    actFinishOrder_etCardExpire.text.toString())

                val dialog = indeterminateProgressDialog(getString(R.string.dlg_progress_wait), getString(R.string.title_payment))
                dialog.show()

                finishOrder(transaction, { result ->
                    if (result == R.string.callback_succes) {
                        dialog.dismiss()
                        toast(getString(R.string.toast_order_success))
                        val order = Order(System.currentTimeMillis(), actFinishOrder_etCardNumber.text.toString().takeLast(4), Cart.getTotalPrice(), actFinishOrder_etCardHolder.text.toString(), System.currentTimeMillis() )

                        val realmInstance = Realm.getDefaultInstance()

                        realmInstance.use { realm ->
                            realm.beginTransaction()
                            realm.insert(order)
                            realm.commitTransaction()
                            Cart.clearCart()
                            setResult(RESULT_OK)
                            finish()
                        }
                    }
                })
            }
        }
    }

    fun finishOrder(transaction: Transaction, callback: (Int) -> Unit) {
        RestService().saveTransaction(transaction, {
            callback(R.string.callback_succes)
        })
    }

    private fun validateFields() : Boolean {
        if (actFinishOrder_etCardCode.text.isEmpty() &&
                actFinishOrder_etCardNumber.text.isEmpty() &&
                actFinishOrder_etCardHolder.text.isEmpty() &&
                actFinishOrder_etCardExpire.text.isEmpty() ) {
            toast(getString(R.string.toast_empty_fields))
            return false
        } else if (actFinishOrder_etCardNumber.length() < 16) {
            toast(getString(R.string.toast_card_number_invalid))
            return false
        }
           return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}