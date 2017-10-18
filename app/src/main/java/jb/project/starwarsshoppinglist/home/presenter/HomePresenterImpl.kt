package jb.project.starwarsshoppinglist.home.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import jb.project.starwarsshoppinglist.home.activity.view.HomeView
import jb.project.starwarsshoppinglist.model.Cart
import jb.project.starwarsshoppinglist.service.ProductRepositoryProvider
import kotlin.properties.Delegates

/**
 * Created by Jb on 12/10/2017.
 */
class HomePresenterImpl : HomePresenter {
    lateinit var mView: HomeView
    private var realm: Realm by Delegates.notNull()

    override fun init(homeView: HomeView) {
        mView = homeView
        realm = Realm.getDefaultInstance()

        loadList()

    }

    override fun loadList() {
        val repository = ProductRepositoryProvider.provideProductRepository()
        repository.loadProducts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    if (result.body()?.count() == 0) {
                        mView.productListNotFound()
                    } else
                        mView.loadProductList(result.body()!!)
                }, { error ->
                    mView.errorLoading()
                })
    }

    override fun getCountCart(): String {
        return realm.where(Cart::class.java).findAll().count().toString()
    }

    override fun destroy() {
        realm.close()
    }

}