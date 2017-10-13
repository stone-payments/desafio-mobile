package jb.project.starwarsshoppinglist.productList.presenter


import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jb.project.starwarsshoppinglist.productList.activity.view.ProductListView
import jb.project.starwarsshoppinglist.service.ProductRepositoryProvider


/**
 * Created by Jb on 12/10/2017.
 */
class ProductListPresenterImpl : ProductListPresenter {


    lateinit var mView: ProductListView


    override fun init(productListView: ProductListView) {
        mView = productListView

        val repository = ProductRepositoryProvider.provideProductRepository()
        repository.loadProducts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe ({
                    result ->
                    mView.loadProductList(result.body()!!)
                }, { error ->
                    error.printStackTrace()
                })

    }


}