package douglasspgyn.com.github.desafiostone.business.controller

import douglasspgyn.com.github.desafiostone.business.model.Product
import douglasspgyn.com.github.desafiostone.business.network.RequestCallback
import douglasspgyn.com.github.desafiostone.business.network.RequestManager
import douglasspgyn.com.github.desafiostone.business.service.ProductService

/**
 * Created by Douglas on 12/11/17.
 */

class ProductController : AbstractController<ProductService>() {

    fun getProducts(callback: RequestCallback<List<Product>>) {
        RequestManager.newRequest(service.products(), callback)
    }
}