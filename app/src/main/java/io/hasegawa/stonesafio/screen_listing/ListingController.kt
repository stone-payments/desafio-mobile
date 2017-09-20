package io.hasegawa.stonesafio.screen_listing

import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.jakewharton.rxbinding2.widget.textChanges
import com.mikepenz.actionitembadge.library.ActionItemBadge
import io.hasegawa.presentation.screen_listing.ListingContract
import io.hasegawa.presentation.screen_listing.ListingContract.ViewState
import io.hasegawa.presentation.screen_listing.ListingPresenter
import io.hasegawa.stonesafio.R
import io.hasegawa.stonesafio.common.BaseController
import io.hasegawa.stonesafio.common.FontCache
import io.hasegawa.stonesafio.common.bindView
import io.hasegawa.stonesafio.screen_navdrawer.NavDrawerController
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class ListingController
    : BaseController<ListingContract.View, ViewState,
        ListingContract.StateEvent, ListingPresenter>(), ListingContract.View {

    private val toolbar: Toolbar by bindView(R.id.listing_toolbar)
    private val toolbarTitleTv: TextView by bindView(R.id.listing_toolbar_title_tv)
    private val searchBarEt: EditText by bindView(R.id.listing_search_et)
    private val productsRv: RecyclerView by bindView(R.id.listing_products_rv)

    private var rvController: ListingRvController? = null
    private var menuCart: MenuItem? = null
    private val cartClicksSubj = PublishSubject.create<Unit>()

    private var actionBarToggler: ActionBarDrawerToggle? = null

    // Save a copy because the menu inflation may happens after the view has restored state
    private var lastNumberInCart: Int = 0

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup, savedViewState: Bundle?): View =
            inflater.inflate(R.layout.screen_listing, container, false)
                    .also { setHasOptionsMenu(true) }

    override fun createPresenter(): ListingPresenter = ListingDIComponent.initialize(this).getPresenter()

    override fun onViewBound() {
        super.onViewBound()

        with(activityCompat!!) {
            setSupportActionBar(toolbar)

            val navController = parentController as? NavDrawerController
            navController?.let { parent ->
                actionBarToggler = ActionBarDrawerToggle(this, parent.drawerLayout, toolbar,
                        R.string.app_name, R.string.app_name)
            }

            with(supportActionBar!!) {
                setDisplayHomeAsUpEnabled(true)
                setHomeButtonEnabled(true)
                setDisplayShowTitleEnabled(false)
            }
            FontCache.brandFont(this, toolbarTitleTv)
        }

        productsRv.layoutManager = LinearLayoutManager(activity)
        rvController = ListingRvController()
        productsRv.adapter = rvController!!.adapter

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.listing, menu)
        menuCart = menu.findItem(R.id.listing_menu_cart)

        val drawable = activityCompat?.getDrawable(R.drawable.ic_cart_outline)
        ActionItemBadge.update(activity, menuCart, drawable,
                ActionItemBadge.BadgeStyles.BLUE, lastNumberInCart)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.listing_menu_cart) {
            cartClicksSubj.onNext(Unit)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        actionBarToggler?.syncState()
    }

    override fun onDestroyView(view: View) {
        productsRv.adapter = null
        rvController = null
        super.onDestroyView(view)
    }

    override fun render(state: ViewState) {
        lastNumberInCart = state.numberInCart
        menuCart?.also { ActionItemBadge.update(it, state.numberInCart) }

        if (baseRestoringViewState) {
            searchBarEt.setText(state.filter, TextView.BufferType.EDITABLE)
        }

        when (state) {
            is ViewState.Okay -> rvController?.setData(null, null, state.products)
            is ViewState.Loading -> rvController?.setData(true, null, null)
            is ViewState.Error -> rvController?.setData(null, state.type, null)
        }
    }

    override fun goToCartScreen(): Observable<Unit> = cartClicksSubj

    override fun addToCart(): Observable<ListingContract.Product> = rvController!!.observeBuyClicks()

    override fun changedFilter(): Observable<String> = searchBarEt.textChanges().map { it.toString() }
}