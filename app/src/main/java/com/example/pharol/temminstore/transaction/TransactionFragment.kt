package com.example.pharol.temminstore.transaction

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pharol.temminstore.R
import com.example.pharol.temminstore.TemminApplication
import com.example.pharol.temminstore.di.ui.DaggerFragmentComponent
import com.example.pharol.temminstore.di.ui.FragmentModule

class TransactionFragment: LifecycleFragment() {

    val component by lazy {
        DaggerFragmentComponent.builder().fragmentModule(FragmentModule(this)).
                applicationComponent((this.context.applicationContext as TemminApplication).applicationComponent).
                build()
    }

    @javax.inject.Inject lateinit var transactionViewFactory : TransactionViewModelFactory

    lateinit var transactionView: TransactionViewModel

    val transactionAdapter = TransactionRecyclerAdapter(arrayListOf())
    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        transactionView = ViewModelProviders.of(this, transactionViewFactory).get(TransactionViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val returnView = inflater?.inflate(R.layout.fragment_transactions, container, false)

        recyclerView = returnView?.findViewById(R.id.rv_list_transactions) as RecyclerView
        recyclerView?.layoutManager = android.support.v7.widget.LinearLayoutManager(activity)
        recyclerView?.adapter = transactionAdapter

        return returnView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        transactionView.getTransactions().observe(this, Observer {
            if (it != null) {
                transactionAdapter.listTransactions = it
            }
            recyclerView?.adapter?.notifyDataSetChanged()
        })

    }
}