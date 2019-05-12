package br.com.stone.vianna.starstore.feature.transactionHistory

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.stone.vianna.starstore.R
import br.com.stone.vianna.starstore.baseClasses.BaseActivity
import br.com.stone.vianna.starstore.entity.PaymentTransaction
import kotlinx.android.synthetic.main.activity_transaction_history.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class TransactionHistoryActivity : BaseActivity(), TransactionContract.View {

    val presenter: TransactionContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_history)

        initializeViews()
        presenter.init()
    }

    private fun initializeViews() {
        transaction_toolbar.title = getString(R.string.transaction_history_screen_title)
        setSupportActionBar(transaction_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this)
        transaction_list.layoutManager = layoutManager
    }

    override fun updateTransactionHistory(transactions: List<PaymentTransaction>) {

        val adapter = TransactionAdapter(transactions)
        transaction_list.adapter = adapter
    }

}