package jb.project.starwarsshoppinglist.home.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jb.project.starwarsshoppinglist.home.activity.view.HomeView
import jb.project.starwarsshoppinglist.productList.activity.view.ProductListView
import jb.project.starwarsshoppinglist.service.ProductRepositoryProvider

/**
 * Created by Jb on 12/10/2017.
 */
class HomePresenterImpl : HomePresenter {
    lateinit var mView: HomeView


    override fun init(homeView: HomeView) {
        mView = homeView

        val repository = ProductRepositoryProvider.provideProductRepository()
        repository.loadProducts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    mView.loadProductList(result.body()!!)
                }, { error ->
                    error.printStackTrace()
                })

    }
}