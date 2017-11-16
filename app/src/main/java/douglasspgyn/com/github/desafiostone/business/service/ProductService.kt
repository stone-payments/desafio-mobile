package douglasspgyn.com.github.desafiostone.business.service

import douglasspgyn.com.github.desafiostone.business.model.Product
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Douglas on 12/11/17.
 */

interface ProductService {

    @GET("stone-pagamentos/desafio-mobile/master/products.json")
    fun products(): Call<List<Product>>
}