package com.partiufast.mercadodoimperador.ui.activities

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.partiufast.mercadodoimperador.model.BillHistory

import com.partiufast.mercadodoimperador.R
import com.partiufast.mercadodoimperador.adapters.BillAdapter
import kotlinx.android.synthetic.main.activity_history.*
import org.jetbrains.anko.alert

class  HistoryActivity : AppCompatActivity() {

    val PREFS_FILE: String = ".com.partiufast.mercadodoimperador.preferences"
    val KEY_BILLS_LIST: String = "KEY_BILLS_LIST"
    var jsonBills: String? = null
    var bills :java.util.ArrayList<BillHistory> = java.util.ArrayList()
    var editor: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)


        if (supportActionBar != null)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val sharedPrefs = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
        editor = sharedPrefs.edit()
        jsonBills = sharedPrefs.getString(KEY_BILLS_LIST, "")
        val billType = object : TypeToken<List<BillHistory>>() {
        }.type
        val gson : Gson = Gson()
        if (gson.fromJson<java.util.ArrayList<BillHistory>>(jsonBills, billType) != null)
            bills = gson.fromJson<java.util.ArrayList<BillHistory>>(jsonBills, billType)

        if (intent.extras!=null) {
                val new_purchase = intent.extras.getParcelable<BillHistory>(getString(R.string.BILL_EXTRA))
                bills.add(new_purchase)
            alert(new_purchase.getAlertString(), "Transação Efetuada") {
                positiveButton("Ok") { }
            }.show()
                alert {  }
                intent.extras.getParcelable<BillHistory>(getString(R.string.BILL_EXTRA)).getAlertString()
                }

        if (bills.size > 0) {
            bill_recycler_view.layoutManager = LinearLayoutManager(this)
            bill_recycler_view.adapter = BillAdapter(bills)
            empty_history_text_view.visibility = View.GONE
        } else {
            empty_history_text_view.visibility = View.VISIBLE
        }
    }

    override fun onPause() {
        val gson = Gson()
        jsonBills = gson.toJson(bills)
        editor!!.putString(KEY_BILLS_LIST, jsonBills)
        editor!!.apply()
        
        super.onPause()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }





}
