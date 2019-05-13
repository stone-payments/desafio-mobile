package br.com.stone.vianna.starstore.baseClasses

import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem

open class BaseActivity: AppCompatActivity(){

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