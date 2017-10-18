package jb.project.starwarsshoppinglist.orderHistory.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.LinearLayout
import io.realm.RealmResults
import jb.project.starwarsshoppinglist.BaseActivity
import jb.project.starwarsshoppinglist.R
import jb.project.starwarsshoppinglist.Utils.DividerItemDecoration
import jb.project.starwarsshoppinglist.model.Purchase
import jb.project.starwarsshoppinglist.orderHistory.activity.view.OrderHistoryView
import jb.project.starwarsshoppinglist.orderHistory.adapter.OrderHistoryAdapter
import jb.project.starwarsshoppinglist.orderHistory.presenter.OrderHistoryPresenter
import jb.project.starwarsshoppinglist.orderHistory.presenter.OrderHistoryPresenterImpl
import kotlinx.android.synthetic.main.activity_order_history.*
import kotlinx.android.synthetic.main.content_order_history.*

class OrderHistoryActivity : BaseActivity(), OrderHistoryView {

    lateinit var mPresenter: OrderHistoryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mPresenter = OrderHistoryPresenterImpl()
        mPresenter.init(this)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun setUpRecyclerView(orderItems: RealmResults<Purchase>) {
        val adapter = OrderHistoryAdapter(orderItems)
        recycler_order.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recycler_order.adapter = adapter
        recycler_order.addItemDecoration(android.support.v7.widget.DividerItemDecoration(this, DividerItemDecoration.VERTICAL))


    }
}


