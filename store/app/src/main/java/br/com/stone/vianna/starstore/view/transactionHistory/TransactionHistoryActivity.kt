package br.com.stone.vianna.starstore.view.transactionHistory

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import br.com.stone.vianna.starstore.R
import br.com.stone.vianna.starstore.baseClasses.BaseActivity
import kotlinx.android.synthetic.main.activity_transaction_history.*

class TransactionHistoryActivity : BaseActivity(), TransactionContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_history)

        initializeViews()
    }

    private fun initializeViews() {
        transaction_toolbar.title = getString(R.string.transaction_history_screen_title)
        setSupportActionBar(transaction_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this)
        transaction_list.layoutManager = layoutManager
    }


}