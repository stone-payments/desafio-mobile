package jb.project.starwarsshoppinglist.orderHistory.activity.view

import org.jetbrains.annotations.NotNull

import io.realm.RealmResults
import jb.project.starwarsshoppinglist.model.Purchase

/**
 * Created by Jb on 17/10/2017.
 */

 interface OrderHistoryView {
    fun setUpRecyclerView(orderItems: RealmResults<Purchase>)

}
