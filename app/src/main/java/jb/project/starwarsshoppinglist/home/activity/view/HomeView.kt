package jb.project.starwarsshoppinglist.home.activity.view

import jb.project.starwarsshoppinglist.model.Product

/**
 * Created by Jb on 12/10/2017.
 */
interface HomeView {
    fun loadProductList(productList: ArrayList<Product>)
}