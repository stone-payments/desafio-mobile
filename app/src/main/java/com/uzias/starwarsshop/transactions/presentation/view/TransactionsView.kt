package com.uzias.starwarsshop.transactions.presentation.view

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.uzias.starwarsshop.R
import com.uzias.starwarsshop.core.presentation.BaseFragment
import com.uzias.starwarsshop.core.presentation.BasePresenter
import com.uzias.starwarsshop.core.presentation.BaseView
import io.reactivex.functions.Action
import javax.inject.Inject
import android.view.ViewGroup
import android.view.LayoutInflater
import com.uzias.starwarsshop.products.presentation.view.ProductsFragment
import com.uzias.starwarsshop.transactions.presentation.adapter.TransactionsAdapter
import com.uzias.starwarsshop.transactions.presentation.model.PresentationTransaction
import com.uzias.starwarsshop.transactions.presentation.presenter.TransactionsPresenter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_transactions.*
import kotlinx.android.synthetic.main.state_view_empty.*
import kotlinx.android.synthetic.main.state_view_error.*

interface TransactionsView : BaseView {
    fun setList(list: List<PresentationTransaction>)
    fun hideRecyclerView()
}

class TransactionsFragment : BaseFragment(), TransactionsView, SwipeRefreshLayout.OnRefreshListener {

    companion object {

        fun newInstance(): TransactionsFragment {
            return TransactionsFragment()
        }

    }

    @Inject
    lateinit var presenter: TransactionsPresenter

    @Inject
    lateinit var adapter: TransactionsAdapter

    override fun onCreateView(inflater: LayoutInflater?, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_transactions, parent, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        injectDependencies()
        setupView()
    }

    private fun setupView() {
        text_empty.text = getString(R.string.label_empty_transactions)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        swiperefreshlayout.setOnRefreshListener(this)
        buttonTryAgain.setOnClickListener {
            onRefresh()
        }
    }

    override fun onResume() {
        super.onResume()
        setupView()
        onRefresh()
    }

    override fun getPresenter(): BasePresenter = presenter

    override fun injectDependencies() {
        AndroidSupportInjection.inject(this)
    }

    override fun setList(list: List<PresentationTransaction>) {
        adapter.clear()
        adapter.setTransactions(list)
    }

    override fun onRefresh() {
        swiperefreshlayout.isRefreshing = false
        presenter.getTransactions()
    }

    override fun enableRefresh(): Action = Action { swiperefreshlayout.isEnabled = true }

    override fun disableRefresh(): Action = Action { swiperefreshlayout.isEnabled = false }

    override fun hideRecyclerView() {
        recyclerView.visibility = View.GONE
    }

}