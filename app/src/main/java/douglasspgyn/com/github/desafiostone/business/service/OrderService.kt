package douglasspgyn.com.github.desafiostone.business.service

import douglasspgyn.com.github.desafiostone.business.model.OrderRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Douglas on 13/11/17.
 */

interface OrderService {

    @POST("/orders")
    fun newOrder(@Body order: OrderRequest): Call<ResponseBody>
}