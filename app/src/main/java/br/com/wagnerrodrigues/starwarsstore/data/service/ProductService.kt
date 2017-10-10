package br.com.wagnerrodrigues.starwarsstore.data.service

import br.com.wagnerrodrigues.starwarsstore.domain.entity.Product
import br.com.wagnerrodrigues.starwarsstore.domain.event.ProductsFetchedEvent
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductService : RestService() {
    fun fetchAll(){
        val jsonCall : Call<List<Product>> = productsBuilder.create(API::class.java).obterProdutos()
        jsonCall.enqueue(object : Callback<List<Product>>{
            override fun onResponse(call: Call<List<Product>>?, response: Response<List<Product>>?) {
                if(response!!.isSuccessful){
                    EventBus.getDefault().post(ProductsFetchedEvent(response.body()))
                }

            }

            override fun onFailure(call: Call<List<Product>>?, t: Throwable?) {
                EventBus.getDefault().post(t)
            }

        })
    }
}