package br.com.ygorcesar.desafiostone.view

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import br.com.ygorcesar.desafiostone.R
import br.com.ygorcesar.desafiostone.data.replace
import br.com.ygorcesar.desafiostone.view.item.ItemsCartFragment
import br.com.ygorcesar.desafiostone.view.item.MainFragment
import br.com.ygorcesar.desafiostone.view.transaction.TransactionsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> replace(MainFragment(), R.id.fragment)
                R.id.action_checkout -> replace(ItemsCartFragment(), R.id.fragment)
                R.id.action_transactions -> replace(TransactionsFragment(), R.id.fragment)
            }
            true
        }
        bottom_nav.selectedItemId = R.id.action_home
    }

    fun selectItemMenu(@IdRes idRes:Int){
        bottom_nav.selectedItemId = idRes
    }
}