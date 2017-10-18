package jb.project.starwarsshoppinglist.orderHistory.presenter

import io.realm.Realm
import jb.project.starwarsshoppinglist.model.Cart
import jb.project.starwarsshoppinglist.model.Purchase
import jb.project.starwarsshoppinglist.orderHistory.activity.view.OrderHistoryView
import kotlin.properties.Delegates

class OrderHistoryPresenterImpl : OrderHistoryPresenter {

    lateinit var mView: OrderHistoryView
    private var realm: Realm by Delegates.notNull()

    override fun init(orderHistoryView: OrderHistoryView) {
        mView = orderHistoryView
        realm = Realm.getDefaultInstance()

        val orderItems = realm.where(Purchase::class.java).findAll()!!
        if (orderItems.count() > 0)
        {
            mView.setUpRecyclerView(orderItems)
        }

    }

}