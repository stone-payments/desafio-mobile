package io.hasegawa.stonesafio.screen_cart

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.ViewFlipper
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import io.hasegawa.presentation.screen_cart.CartContract
import io.hasegawa.presentation.screen_cart.CartContract.SummaryViewState
import io.hasegawa.presentation.screen_cart.CartPresenter
import io.hasegawa.stonesafio.R
import io.hasegawa.stonesafio.common.BaseController
import io.hasegawa.stonesafio.common.bindView
import io.hasegawa.stonesafio.common.showNextUntilViewId
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class CartController
    : BaseController<CartContract.View, CartContract.ViewState, CartContract.StateEvent, CartPresenter>(),
        CartContract.View {

    private val pageTv: TextView by bindView(R.id.cart_page_tv)
    private val pageFlipper: ViewFlipper by bindView(R.id.cart_flipper)

    // Cart list page
    private val totalTv: TextView by bindView(R.id.cart_total_tv)
    private val confirmCartBt: Button by bindView(R.id.cart_confirm_cart_bt)
    private val productsRv: RecyclerView by bindView(R.id.cart_products_rv)

    // Payment page
    private val cardNumberTiet: TextInputEditText by bindView(R.id.cart_payment_card_number_tiet)
    private val cardNameTiet: TextInputEditText by bindView(R.id.cart_payment_card_name_tiet)
    private val cardCVVTiet: TextInputEditText by bindView(R.id.cart_payment_card_cvv_tiet)
    private val cardExpMonthTiet: TextInputEditText by bindView(R.id.cart_payment_card_exp_month_tiet)
    private val cardExpYearTiet: TextInputEditText by bindView(R.id.cart_payment_card_exp_year_tiet)
    private val paymentConfirmBt: Button by bindView(R.id.cart_payment_confirm_bt)

    // Summary page
    private val summaryFlipper: ViewFlipper by bindView(R.id.cart_summary_flipper)

    private val backButtonSubj = PublishSubject.create<Unit>()
    private var rvController: CartRvController? = null

    override fun createPresenter(): CartPresenter = CartDIComponent.initialize(this).getPresenter()

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup, savedViewState: Bundle?): View =
            inflater.inflate(R.layout.screen_cart, container, false)

    override fun onViewBound() {
        productsRv.layoutManager = LinearLayoutManager(activity)
        rvController = CartRvController().also {
            productsRv.adapter = it.adapter
        }
    }

    override fun render(state: CartContract.ViewState) {
        pageTv.text = state.currentPage.toString()
        val pageId = when (state.currentPage) {
            CartContract.Page.Cart -> R.id.cart_list_ll
            CartContract.Page.Payment -> R.id.cart_payment_ll
            CartContract.Page.Summary -> R.id.cart_summary_ll
        }
        pageFlipper.showNextUntilViewId(pageId)

        // Cart page
        totalTv.text = "R\$${state.productsTotal}" // TODO[hase] proper price formatting
        confirmCartBt.isEnabled = state.canProceedToPaymentScreen
        rvController!!.setData(state.products)

        if (baseRestoringViewState) {
            state.ccInfo.run {
                cardNumberTiet.setText(number, TextView.BufferType.EDITABLE)
                cardNameTiet.setText(name, TextView.BufferType.EDITABLE)
                cardCVVTiet.setText(cvv, TextView.BufferType.EDITABLE)
                cardExpMonthTiet.setText(expDateMonth, TextView.BufferType.EDITABLE)
                cardExpYearTiet.setText(expDateYear, TextView.BufferType.EDITABLE)
            }
        }

        // Payment page
        paymentConfirmBt.isEnabled = state.canConfirmPayment
        state.ccInfo.run {
            fun TextInputEditText.check(b: Boolean, msg: () -> String) {
                this.error = when (b) {
                    true -> null
                    else -> msg() // TODO[hase] proper string resources
                }
            }
            cardNumberTiet.check(numberValid) { "Number invalid" }
            cardNameTiet.check(nameValid) { "Name invalid" }
            cardCVVTiet.check(cvvValid) { "CVV invalid" }
            cardExpMonthTiet.check(expDateMonthValid) { "Month invalid" }
            cardExpYearTiet.check(expDateYearValid) { "Year invalid" }
        }

        // Summary page
        val summaryPageId = when (state.summaryViewState) {
            is SummaryViewState.Loading -> R.id.cart_summary_loading_ll
            is SummaryViewState.Success -> R.id.cart_summary_success_ll
            is SummaryViewState.Error -> R.id.cart_summary_error_ll
        }
        summaryFlipper.showNextUntilViewId(summaryPageId)
    }

    override fun handleBack(): Boolean {
        backButtonSubj.onNext(Unit)
        return true
    }

    override fun confirmCartList(): Observable<Unit> = confirmCartBt.clicks()
    override fun confirmPayment(): Observable<Unit> = paymentConfirmBt.clicks()
    override fun back(): Observable<Unit> = backButtonSubj
    override fun removeItemFromCart(): Observable<String> = rvController!!.observeRemoveClicks().map { it.id }
    override fun changesCCNumber(): Observable<String> = cardNumberTiet.textChanges().map { it.toString() }
    override fun changesCCName(): Observable<String> = cardNameTiet.textChanges().map { it.toString() }
    override fun changesCCVerificationNumber(): Observable<String> = cardCVVTiet.textChanges().map { it.toString() }
    override fun changesExpDateMonth(): Observable<String> = cardExpMonthTiet.textChanges().map { it.toString() }
    override fun changesExpDateYear(): Observable<String> = cardExpYearTiet.textChanges().map { it.toString() }
}