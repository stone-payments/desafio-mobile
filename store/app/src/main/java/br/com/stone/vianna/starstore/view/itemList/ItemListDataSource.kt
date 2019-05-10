package br.com.stone.vianna.starstore.view.itemList

import br.com.stone.vianna.starstore.entity.Item
import io.reactivex.Observable
import retrofit2.http.GET

interface ItemListDataSource {

    @GET("products.json")
    fun getItems(): Observable<List<Item>>
}