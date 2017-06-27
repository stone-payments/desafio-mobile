package com.example.pharol.temminstore.item

import android.arch.lifecycle.LiveData
import android.content.Context
import android.util.Log
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.net.ConnectivityManager
import com.example.pharol.temminstore.WebService


@Singleton
class  ItemRepository @Inject constructor(val webService: WebService, val itemDao : ItemDAO, val context:
Context){

    var lastUpdate = 0L

    fun getItemList() : LiveData<List<Item>> {
        refreshItems()
        return itemDao.getAll()
    }

    fun getItem(id: Int) : LiveData<Item> {
        return itemDao.getItem(id)
    }

    /**
     *  Request Items from server and update, delete or insert items on database if necessary.
     *  Only executes if the last update was more than 10 minutes ago( save 3g from users!!)
     */
    private fun refreshItems() {
        if( isNetworkAvailable() && System.currentTimeMillis() - lastUpdate > 600000 ) {
            var hasItems = false
            webService.getItemList().enqueue(object : Callback<List<Item>> {

                override fun onFailure(call: Call<List<Item>>?, t: Throwable?) {
                    Log.e("temminStore","falha ao acessar servidor")
                }

                override fun onResponse(call: Call<List<Item>>?, response: Response<List<Item>>) {
                    val list = response.body()

                    //Should not use the Main thread for DB!
                    Executors.newSingleThreadExecutor().execute {
                        //Get IDs from Items that already exists(prevent duplicates) and assign ID 0 for new items
                        val existingItems = list.map {
                            Item(itemDao.getItemID(it.title), it.title, it.price, it.seller, it.thumbnailHd)
                        }

                        //Insert and/or Update
                        itemDao.save(existingItems)

                        //Delete Items that doesn't exist on the server
                        itemDao.refreshBase(list.map { itemDao.getItemID(it.title) })
                    }

                }

            })
        }
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager =  context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}



