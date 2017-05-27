package br.com.ygorcesar.desafiostone.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import br.com.ygorcesar.desafiostone.R
import br.com.ygorcesar.desafiostone.data.replace
import br.com.ygorcesar.desafiostone.data.replaceToStack
import br.com.ygorcesar.desafiostone.view.item.ItemsCartFragment
import br.com.ygorcesar.desafiostone.view.item.MainFragment
import br.com.ygorcesar.desafiostone.view.transaction.TransactionsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        replace(MainFragment(), R.id.fragment)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_checkout -> replaceToStack(ItemsCartFragment(), R.id.fragment)
            R.id.action_transactions -> replaceToStack(TransactionsFragment(), R.id.fragment)
        }
        return super.onOptionsItemSelected(item)
    }
}
