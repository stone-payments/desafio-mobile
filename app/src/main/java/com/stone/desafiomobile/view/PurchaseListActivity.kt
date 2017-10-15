package com.stone.desafiomobile.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.stone.desafiomobile.R

class PurchaseListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_list)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
