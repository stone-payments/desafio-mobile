package jb.project.starwarsshoppinglist.cart.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import io.realm.RealmResults
import jb.project.starwarsshoppinglist.BaseActivity
import jb.project.starwarsshoppinglist.R
import jb.project.starwarsshoppinglist.Utils.DividerItemDecoration
import jb.project.starwarsshoppinglist.Utils.Mask
import jb.project.starwarsshoppinglist.cart.activity.view.CartnCheckoutView
import jb.project.starwarsshoppinglist.cart.adapter.CartnCheckoutAdapter
import jb.project.starwarsshoppinglist.cart.listener.ItemClickListener
import jb.project.starwarsshoppinglist.cart.presenter.CartnCheckoutPresenter
import jb.project.starwarsshoppinglist.cart.presenter.CartnCheckoutPresenterImpl
import jb.project.starwarsshoppinglist.model.Cart
import jb.project.starwarsshoppinglist.orderHistory.activity.OrderHistoryActivity
import kotlinx.android.synthetic.main.activity_checkout.*

@SuppressLint("SetTextI18n")
class CartnCheckoutActivity : BaseActivity(), ItemClickListener, CartnCheckoutView {

    private var mSumItems: Int = 0
    private lateinit var mPresenter: CartnCheckoutPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        mPresenter = CartnCheckoutPresenterImpl()
        mPresenter.init(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        input_card_exp.addTextChangedListener(Mask.insert(Mask.MaskType.DATEMONTHYEAR, input_card_exp))
        button_pay_cart.setOnClickListener { payCartCLickButton() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

    private fun payCartCLickButton() {
        mPresenter.validateCartItems(
                input_card_number.text.toString(),
                input_card_name.text.toString(),
                input_card_exp.text.toString(),
                input_card_cvv.text.toString(),
                mSumItems
        )
    }

    override fun cardCvvError(msgId: Int) {
        input_card_cvv.error = getString(msgId)
        input_card_cvv.requestFocus()
    }

    override fun emptyCart(msgId: Int) {
        showToast(msgId)
    }

    override fun cardExpError(msgId: Int) {
        input_card_exp.error = getString(msgId)
        input_card_exp.requestFocus()
    }

    override fun cardTextError(msgId: Int) {
        input_card_name.error = getString(msgId)
        input_card_name.requestFocus()
    }

    override fun cardNumberError(msgId: Int) {
        input_card_number.error = getString(msgId)
        input_card_number.requestFocus()

    }

    override fun orderPurchaseSuccessful() {
        val mIntent = Intent(this, OrderHistoryActivity::class.java)
        startActivity(mIntent)
    }

    override fun showToast(msgId: Int) {
        Toast.makeText(this, msgId, Toast.LENGTH_SHORT).show()
    }

    override fun setUpRecyclerView(cartItems: RealmResults<Cart>) {

        val adapter = CartnCheckoutAdapter(cartItems)
        recycler_cart.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recycler_cart.adapter = adapter
        adapter.setItemClickListener(this)
        recycler_cart.addItemDecoration(android.support.v7.widget.DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        mSumItems = mPresenter.getCartValue()
        txt_sum_items.text = "Valor total: " + "R$ " + String.format("%.2f", mSumItems.div(100.0))
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.destroy()
    }


    override fun onClick(title: String) {
        mPresenter.deleteRowCart(title)
        mCountCart = mPresenter.getCountCart()
        mSumItems = mPresenter.getCartValue()
        txt_sum_items.text = "Valor total: " + "R$ " + String.format("%.2f", mSumItems.div(100.0))
    }

    override fun onSpinnerSelected(title: String, amount: Int) {
        mPresenter.changeRowCart(title, amount)
        mSumItems = mPresenter.getCartValue()
        txt_sum_items.text = "Valor total: " + "R$ " + String.format("%.2f", mSumItems.div(100.0))
    }
}
