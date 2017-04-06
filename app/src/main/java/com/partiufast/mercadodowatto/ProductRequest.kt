package com.partiufast.mercadodowatto

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URL

/**
 * Created by Miguel on 03/04/2017.
 */

class ProductRequest(val url: String) {

    fun run(): Product{
        val productsJsonStr = URL(url).readText()
        Log.d(javaClass.simpleName, productsJsonStr)
        return Gson().fromJson(productsJsonStr, object : TypeToken<List<Product>>() {}.type)
    }
}