package jb.project.starwarsshoppinglist.productDetail.presenter

import io.realm.Realm
import jb.project.starwarsshoppinglist.model.Cart
import jb.project.starwarsshoppinglist.productDetail.activity.view.ProductDetailView
import kotlin.properties.Delegates

class ProductDetailPresenterImpl : ProductDetailPresenter {

    lateinit var mView: ProductDetailView
    private var realm: Realm by Delegates.notNull()

    override fun init(productDetailview: ProductDetailView) {
        mView = productDetailview
        realm = Realm.getDefaultInstance()

    }

    override fun getCountCart(): String {
        return realm.where(Cart::class.java).findAll().count().toString()
    }


    override fun addCart(cart: Cart) {
        realm.executeTransaction {
            realm.copyToRealmOrUpdate(cart)
        }

    }

    override fun destroy() {
        realm.close()
    }
}
