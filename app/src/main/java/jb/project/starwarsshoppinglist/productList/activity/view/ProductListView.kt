package jb.project.starwarsshoppinglist.productList.activity.view


import jb.project.starwarsshoppinglist.model.Product

/**
 * Created by Jb on 12/10/2017.
 */
 interface ProductListView {
    fun loadProductList(productList: MutableList<Product>) {}
    fun showError(strResId: Int) {
        
    }

    fun showError(strResId: String)
    fun showMessage(strResId: Int)
}