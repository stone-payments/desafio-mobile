package br.com.stone.vianna.starstore.feature.itemList

import br.com.stone.vianna.starstore.entity.Item
import io.reactivex.Observable
import retrofit2.http.GET

interface ItemListApi {

    @GET("products.json")
    fun getItems(): Observable<List<Item>>
}