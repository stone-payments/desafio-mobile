package io.hasegawa.stonesafio.screen_cart

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.ViewFlipper
import com.airbnb.epoxy.EpoxyTouchHelper
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import com.layer_net.stepindicator.StepIndicator
import io.hasegawa.presentation.screen_cart.CartContract
import io.hasegawa.presentation.screen_cart.CartContract.SummaryViewState
import io.hasegawa.presentation.screen_cart.CartPresenter
import io.hasegawa.stonesafio.R
import io.hasegawa.stonesafio.common.BaseController
import io.hasegawa.stonesafio.common.bindView
import io.hasegawa.stonesafio.common.showNextUntilViewId
import io.hasegawa.stonesafio.domain.cart.CartProduct
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class CartController
    : BaseController<CartContract.View, CartContract.ViewState, CartContract.StateEvent, CartPresenter>(),
        CartContract.View {

    private val toolbar: Toolbar by bindView(R.id.cart_toolbar)
    private val stepIndicator: StepIndicator by bindView(R.id.cart_stepindicator)
    private val pageFlipper: ViewFlipper by bindView(R.id.cart_flipper)
    private val scrollView: NestedScrollView by bindView(R.id.cart_scrollview)

    // Cart list page
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
    private val productSwipedSub = PublishSubject.create<CartProduct>()
    private var rvController: CartRvController? = null

    override fun createPresenter(): CartPresenter = CartDIComponent.initialize(this).getPresenter()

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup, savedViewState: Bundle?): View =
            inflater.inflate(R.layout.screen_cart, container, false)

    override fun onViewBound() {
        with(activityCompat!!) {
            setSupportActionBar(toolbar)
            supportActionBar?.title = getString(R.string.cart_screen_title)
        }

        productsRv.layoutManager = LinearLayoutManager(activity)
        rvController = CartRvController().also {
            productsRv.adapter = it.adapter
        }

        EpoxyTouchHelper.initSwiping(productsRv)
                .leftAndRight()
                .withTarget(CartRvProductModel::class.java)
                .andCallbacks(object : EpoxyTouchHelper.SwipeCallbacks<CartRvProductModel>() {
                    override fun onSwipeCompleted(model: CartRvProductModel?, itemView: View?,
                                                  position: Int, direction: Int) {
                        model?.product?.also { productSwipedSub.onNext(it) }
                    }
                })
    }

    override fun render(state: CartContract.ViewState) {
        val pageId = when (state.currentPage) {
            CartContract.Page.Cart -> R.id.cart_products_rv
            CartContract.Page.Payment -> R.id.cart_payment_ll
            CartContract.Page.Summary -> R.id.cart_summary_ll
        }
        if (pageFlipper.currentView.id != pageId) {
            pageFlipper.showNextUntilViewId(pageId)
            scrollView.scrollTo(0, 0)
        }
        stepIndicator.currentStepPosition = state.currentPage.ordinal

        // Cart page
        val totalPrice = "R\$${state.productsTotal}" // TODO[hase] proper price formatting
        val total = activityCompat?.getString(R.string.cart_total, totalPrice)
        val canConfirm = state.canProceedToPaymentScreen
        rvController!!.setData(state.products, total, canConfirm)

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
            fun TextInputEditText.check(b: Boolean, msgId: () -> Int) {
                this.error = when (b) {
                    true -> null
                    else -> activity?.getString(msgId())
                }
            }
            cardNumberTiet.check(numberValid) { R.string.cart_payment_cc_number_error }
            cardNameTiet.check(nameValid) { R.string.cart_payment_cc_name_error }
            cardCVVTiet.check(cvvValid) { R.string.cart_payment_cc_cvv_error }
            cardExpMonthTiet.check(expDateMonthValid) { R.string.cart_payment_cc_exp_month_error }
            cardExpYearTiet.check(expDateYearValid) { R.string.cart_payment_cc_exp_year_error }
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

    override fun confirmCartList(): Observable<Unit> = rvController!!.observeConfirmClicks()
    override fun confirmPayment(): Observable<Unit> = paymentConfirmBt.clicks()
    override fun back(): Observable<Unit> = backButtonSubj
    override fun removeItemFromCart(): Observable<String> = productSwipedSub.map { it.id }
    override fun changesCCNumber(): Observable<String> = cardNumberTiet.textChanges().map { it.toString() }
    override fun changesCCName(): Observable<String> = cardNameTiet.textChanges().map { it.toString() }
    override fun changesCCVerificationNumber(): Observable<String> = cardCVVTiet.textChanges().map { it.toString() }
    override fun changesExpDateMonth(): Observable<String> = cardExpMonthTiet.textChanges().map { it.toString() }
    override fun changesExpDateYear(): Observable<String> = cardExpYearTiet.textChanges().map { it.toString() }
}