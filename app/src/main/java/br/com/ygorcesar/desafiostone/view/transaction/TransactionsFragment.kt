package br.com.ygorcesar.desafiostone.view.transaction

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ygorcesar.desafiostone.R
import br.com.ygorcesar.desafiostone.model.Transaction
import com.vicpin.krealmextensions.queryAll
import kotlinx.android.synthetic.main.fragment_main.*

class TransactionsFragment : android.support.v4.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragment()
    }

    fun setupFragment() {
        rv_items.setHasFixedSize(true)
        rv_items.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_items.adapter = AdapterTransactions(Transaction().queryAll())
    }
}