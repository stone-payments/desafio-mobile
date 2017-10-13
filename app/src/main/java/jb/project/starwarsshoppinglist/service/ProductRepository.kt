package jb.project.starwarsshoppinglist.service

import io.reactivex.Observable
import jb.project.starwarsshoppinglist.model.Product
import retrofit2.Response

/**
 * Created by Jb on 13/10/2017.
 */
class ProductRepository (val apiService: ProductListService) {
        fun loadProducts(): Observable<Response<MutableList<Product>>> {
            return apiService.fetchProductList()
        }
}