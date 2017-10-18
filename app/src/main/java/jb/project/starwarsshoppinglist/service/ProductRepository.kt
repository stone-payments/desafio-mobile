package jb.project.starwarsshoppinglist.service

import io.reactivex.Observable
import jb.project.starwarsshoppinglist.model.Product
import jb.project.starwarsshoppinglist.model.Purchase
import retrofit2.Response

/**
 * Created by Jb on 13/10/2017.
 */
class ProductRepository(private val apiService: ProductListService) {
    fun loadProducts(): Observable<Response<ArrayList<Product>>> {
        return apiService.fetchProductList()
    }

    fun initPurchase(purchaseObj: Purchase): Observable<Response<Purchase>> {
        return apiService.savePurchase(purchaseObj)
    }
}