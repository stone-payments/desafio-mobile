package jb.project.starwarsshoppinglist.orderHistory.presenter

import jb.project.starwarsshoppinglist.orderHistory.activity.OrderHistoryActivity
import jb.project.starwarsshoppinglist.orderHistory.activity.view.OrderHistoryView

/**
 * Created by Jb on 17/10/2017.
 */
interface OrderHistoryPresenter {
    fun init(orderHistoryView: OrderHistoryView)
}