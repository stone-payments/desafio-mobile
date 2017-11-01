package com.uzias.starwarsshop.products.presentation.view

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.uzias.starwarsshop.R
import com.uzias.starwarsshop.core.presentation.BaseFragment
import com.uzias.starwarsshop.core.presentation.BasePresenter
import com.uzias.starwarsshop.core.presentation.BaseView
import com.uzias.starwarsshop.products.presentation.adapter.ProductsAdapter
import com.uzias.starwarsshop.products.presentation.model.PresentationProduct
import io.reactivex.functions.Action
import kotlinx.android.synthetic.main.fragment_products.*
import kotlinx.android.synthetic.main.state_view_error.*
import javax.inject.Inject
import android.view.ViewGroup
import android.view.LayoutInflater
import com.uzias.starwarsshop.products.presentation.adapter.ProductsListener
import com.uzias.starwarsshop.products.presentation.presenter.ProductsPresenter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.state_view_empty.*
import com.uzias.starwarsshop.util.changeTextWithAnimation
import java.text.NumberFormat

interface ProductsView : BaseView {
    fun setList(list: List<PresentationProduct>)
    fun showButtonBottom()
    fun hideButtonBottom()
    fun setTextButtonTotal(totalPrice: Double)
}

class ProductsFragment : BaseFragment(), ProductsView, SwipeRefreshLayout.OnRefreshListener, ProductsListener {

    companion object {

        fun newInstance(): ProductsFragment {
            return ProductsFragment()
        }

    }

    @Inject
    lateinit var presenter: ProductsPresenter

    @Inject
    lateinit var productsAdapter: ProductsAdapter

    override fun onCreateView(inflater: LayoutInflater?, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_products, parent, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDependencies()
        setupView()
    }

    private fun setupView() {
        text_empty.text = getString(R.string.label_empty_products)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = productsAdapter
        productsAdapter.setListener(this)
        swiperefreshlayout.setOnRefreshListener(this)
        buttonTryAgain.setOnClickListener {
            onRefresh()
        }
        buttonCheckout.setOnClickListener {
            presenter.clickedButtonCheckout()
        }
    }

    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    override fun getPresenter(): BasePresenter = presenter

    override fun injectDependencies() {
        AndroidSupportInjection.inject(this)
    }

    override fun setList(list: List<PresentationProduct>) {
        productsAdapter.clear()
        productsAdapter.setProducts(list)
    }

    override fun onRefresh() {
        swiperefreshlayout.isRefreshing = false
        presenter.getProducts()
    }

    override fun enableRefresh(): Action = Action { swiperefreshlayout.isEnabled = true }

    override fun disableRefresh(): Action = Action { swiperefreshlayout.isEnabled = false }

    override fun clickedButtonAdd(position: Int) {
        presenter.clickedButtonAdd(position)
    }

    override fun clickedButtonRemove(position: Int) {
        presenter.clickedButtonRemove(position)
    }

    override fun showButtonBottom() {
        buttonCheckout.visibility = View.VISIBLE
        buttonTotal.visibility = View.VISIBLE
    }

    override fun hideButtonBottom() {
        buttonCheckout.visibility = View.GONE
        buttonTotal.visibility = View.GONE
    }

    override fun setTextButtonTotal(totalPrice: Double) {
        val totalFormatted = getString(R.string.label_total_shop,
                NumberFormat.getCurrencyInstance().format(totalPrice))
        if (buttonTotal.text == null || buttonTotal.text.isEmpty()){
            buttonTotal.text = totalFormatted
        }else {
            buttonTotal.changeTextWithAnimation(totalFormatted)
        }
    }

}