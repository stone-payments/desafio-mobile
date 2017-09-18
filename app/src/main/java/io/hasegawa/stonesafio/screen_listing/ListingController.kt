package io.hasegawa.stonesafio.screen_listing

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import io.hasegawa.presentation.screen_listing.ListingContract
import io.hasegawa.presentation.screen_listing.ListingPresenter
import io.hasegawa.stonesafio.R
import io.hasegawa.stonesafio.common.BaseController
import io.hasegawa.stonesafio.common.bindView
import io.reactivex.Observable


class ListingController
    : BaseController<ListingContract.View, ListingContract.ViewState,
        ListingContract.StateEvent, ListingPresenter>(), ListingContract.View {

    private val goToCartBt: Button by bindView(R.id.listing_go_to_cart_bt)
    private val goToTransactionsBt: Button by bindView(R.id.listing_go_to_transactions_bt)
    private val numInCartTv: TextView by bindView(R.id.listing_num_in_cart_tv)
    private val searchBarEt: EditText by bindView(R.id.listing_search_et)
    private val productsRv: RecyclerView by bindView(R.id.listing_products_rv)

    private var rvController: ListingRvController? = null

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup, savedViewState: Bundle?): View =
            inflater.inflate(R.layout.screen_listing, container, false)

    override fun createPresenter(): ListingPresenter = ListingDIComponent.initialize(this).getPresenter()

    override fun onViewBound() {
        super.onViewBound()

        productsRv.layoutManager = LinearLayoutManager(activity)
        rvController = ListingRvController()
        productsRv.adapter = rvController!!.adapter
    }

    override fun onDestroyView(view: View) {
        productsRv.adapter = null
        rvController = null
        super.onDestroyView(view)
    }

    override fun render(state: ListingContract.ViewState) {
        numInCartTv.text = state.numberInCart.toString()
        if (baseRestoringViewState) {
            searchBarEt.setText(state.filter, TextView.BufferType.EDITABLE)
        }

        when (state) {
            is ListingContract.ViewState.Okay -> {
                rvController?.setData(state.products)
            }
        }
    }

    override fun goToCartScreen(): Observable<Unit> = goToCartBt.clicks()

    override fun goToTransactionsScreen(): Observable<Unit> = goToTransactionsBt.clicks()

    override fun addToCart(): Observable<ListingContract.Product> = rvController!!.observeBuyClicks()

    override fun changedFilter(): Observable<String> = searchBarEt.textChanges().map { it.toString() }
}