package com.stone.desafiomobile.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.stone.desafiomobile.database.ProductDAO
import com.stone.desafiomobile.model.Product
import com.stone.desafiomobile.network.RestRepository
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

/**
 * ViewModel de lista de produtos
 */
class ProductsListVm() : ViewModel() {

    @Inject
    lateinit var restRepository: RestRepository

    @Inject
    lateinit var productDAO: ProductDAO

    /**
     * @return produtos que estao salvos do banco
     */
    val products: LiveData<List<Product>>
        get() {
            return productDAO.listAll()
        }

    /**
     * Itens que estao no carrinho
     */
    var cartItens: ArrayList<Product> = ArrayList()

    /**
     * Recuperar os produtos da api
     * @param loadFinishedCallback avisa quando a requisição terminou
     */
    fun loadProducts(loadFinishedCallback: () -> Unit = {}) {
        restRepository.getProducts({
            // salva produtos no banco
            if (it != null) {
                launch(CommonPool) {
                    productDAO.insertBatch(it)
                }
            }
            loadFinishedCallback()
        }, {
            loadFinishedCallback()
        })
    }

    /**
     * Define o valor total dos itens que estao no carrinho
     * @return valor total
     */
    fun defineValue(): Long {
        var value = 0L
        cartItens.map { product -> value += product.price ?: 0 }
        return value
    }
}
