package douglasspgyn.com.github.desafiostone.business.controller

import douglasspgyn.com.github.desafiostone.business.model.OrderRequest
import douglasspgyn.com.github.desafiostone.business.network.RequestCallback
import douglasspgyn.com.github.desafiostone.business.network.RequestManager
import douglasspgyn.com.github.desafiostone.business.service.OrderService
import okhttp3.ResponseBody

/**
 * Created by Douglas on 13/11/17.
 */

class OrderController : AbstractController<OrderService>() {

    fun createOrder(order: OrderRequest, callback: RequestCallback<ResponseBody>) {
        RequestManager.newRequest(serviceApiary.newOrder(order), callback)
    }
}