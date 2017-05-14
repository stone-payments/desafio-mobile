package com.partiufast.mercadodoimperador

import org.json.JSONArray
import java.math.BigDecimal
import java.net.URL

class ProductRequest(val url: String) {

    fun run(): ArrayList<Product> {
        val productsJsonStr = URL(url).readText()


        /* val Mapper = jacksonObjectMapper()
         val productList: ArrayList<Product> = Mapper.readValue(productsJsonStr)
 */
        val productList: ArrayList<Product> = ArrayList<Product>()
        val jsonArray = JSONArray(productsJsonStr)
        for (index in (0..jsonArray.length() - 1)) {
            val row = jsonArray.getJSONObject(index)
            productList.add(Product(row.getString("title"), BigDecimal(row.getInt("price")).scaleByPowerOfTen(-2), row.getString("zipcode"),
                    row.getString("seller"), row.getString("thumbnailHd"), row.getString("date"), 0))
        }
        return productList
    }
}