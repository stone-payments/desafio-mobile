package jb.project.starwarsshoppinglist.productList.presenter

import jb.project.starwarsshoppinglist.productList.activity.view.ProductListView

/**
 * Created by Jb on 12/10/2017.
 */
interface ProductListPresenter {
    fun init(productListView: ProductListView)
}