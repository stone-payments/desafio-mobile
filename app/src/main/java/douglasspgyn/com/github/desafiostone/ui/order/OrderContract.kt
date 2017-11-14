package douglasspgyn.com.github.desafiostone.ui.order

import douglasspgyn.com.github.desafiostone.business.model.Order

/**
 * Created by Douglas on 13/11/17.
 */

interface OrderContract {
    interface View {
        fun ordersLoaded(orders:List<Order>)
        fun ordersEmpty()
        fun ordersFailed()
    }

    interface Presenter {
        fun loadOrders()
    }
}