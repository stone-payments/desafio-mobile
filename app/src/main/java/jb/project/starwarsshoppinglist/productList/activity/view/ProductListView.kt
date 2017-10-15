package jb.project.starwarsshoppinglist.productList.activity.view


import jb.project.starwarsshoppinglist.model.Product

/**
 * Created by Jb on 12/10/2017.
 */
 interface ProductListView {
    fun loadProductList(productList: ArrayList<Product>) {}

}