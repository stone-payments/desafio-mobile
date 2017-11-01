package com.uzias.starwarsshop.products.presentation.presenter

import com.uzias.starwarsshop.core.behaviours.BehavioursCoordinator
import com.uzias.starwarsshop.core.presentation.BasePresenter
import com.uzias.starwarsshop.core.presentation.lifecycles.LifecycleStrategist
import com.uzias.starwarsshop.products.domain.model.Product
import com.uzias.starwarsshop.products.domain.usecase.GetProducts
import com.uzias.starwarsshop.products.presentation.mapper.PresentationProductMapper
import com.uzias.starwarsshop.products.presentation.model.PresentationProduct
import com.uzias.starwarsshop.products.presentation.navigation.ProductsNavigation
import com.uzias.starwarsshop.products.presentation.view.ProductsView
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.subscribeBy

interface ProductsPresenter: BasePresenter {
    fun getProducts()
    fun clickedButtonAdd(position: Int)
    fun clickedButtonRemove(position: Int)
    fun clickedButtonCheckout()
}

class ProductsPresenterImpl(private val getProducts: GetProducts,
                            private val coordinator: BehavioursCoordinator<Any>,
                            private val strategist: LifecycleStrategist,
                            private val view: ProductsView,
                            private val navigation: ProductsNavigation,
                            private val ioScheduler: Scheduler,
                            private val uiScheduler: Scheduler): ProductsPresenter {

    private var products: MutableList<PresentationProduct> = mutableListOf()

    private var totalPrice: Double = 0.0

    @Suppress("UNCHECKED_CAST")
    override fun getProducts() {
       if (products.isEmpty()){
           val getProducts = getProducts.run()
                   .compose(coordinator as BehavioursCoordinator<List<Product>>)
                   .subscribeOn(ioScheduler)
                   .map(PresentationProductMapper::transformToList)
                   .observeOn(uiScheduler)
                   .doOnNext {
                       products = it.toMutableList()
                       view.setList(products)
                   }
                   .subscribeBy(onError = {})
           strategist.applyStrategy(getProducts)
       }else{
           view.setList(products)
       }
    }

    override fun clickedButtonAdd(position: Int) {
        addProduct(position)
    }

    override fun clickedButtonRemove(position: Int) {
        removeProduct(position)
    }

    override fun clickedButtonCheckout() {
        navigation.goToCheckout(totalPrice = totalPrice)
    }

    private fun addProduct(position: Int){
        manageProducts(position, true)
    }

    private fun removeProduct(position: Int){
        manageProducts(position, false)
    }

    private fun manageProducts(position: Int, addItemToProduct: Boolean){
        val product = products[position]
        val price = if (addItemToProduct) product.price else -product.price
        totalPrice += price
        product.totalItem += if (addItemToProduct) 1 else -1
        view.setList(products)

        showOrHideButtonNext()
        view.setTextButtonTotal(totalPrice)
    }

    private fun showOrHideButtonNext() {
        if (totalPrice == 0.0){
            view.hideButtonBottom()
        }else{
            view.showButtonBottom()
        }
    }

}