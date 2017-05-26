package br.com.ygorcesar.desafiostone.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import br.com.ygorcesar.desafiostone.R
import br.com.ygorcesar.desafiostone.view.item.MainFragment
import br.com.ygorcesar.desafiostone.view.transaction.CheckoutFragment
import br.com.ygorcesar.desafiostone.view.transaction.TransactionsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, MainFragment())
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_checkout -> replaceMainFragment(CheckoutFragment())
            R.id.action_transactions -> replaceMainFragment(TransactionsFragment())
        }
        return super.onOptionsItemSelected(item)
    }

    fun replaceMainFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, fragment)
                .addToBackStack(null)
                .commit()
    }
}
