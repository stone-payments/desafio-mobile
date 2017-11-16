package douglasspgyn.com.github.desafiostone.ui.order

import douglasspgyn.com.github.desafiostone.application.App.Companion.orderDao

/**
 * Created by Douglas on 13/11/17.
 */

class OrderPresenter(val view: OrderContract.View) : OrderContract.Presenter {

    override fun loadOrders() {
        val dbOrders = orderDao?.getOrders()
        
        if (dbOrders != null) {
            if (dbOrders.isNotEmpty()) {
                view.ordersLoaded(dbOrders)
            } else {
                view.ordersEmpty()
            }
        } else {
            view.ordersFailed()
        }
    }
}