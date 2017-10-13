package com.stone.desafiomobile.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.stone.desafiomobile.R

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.action_purchase_log -> {
                val intent = Intent(this, PurchaseListActivity::class.java)
                startActivity(intent)
                return true
            }

            else ->
                return super.onOptionsItemSelected(item)
        }
    }
}