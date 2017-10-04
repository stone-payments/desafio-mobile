package personal.pedrofigueiredo.milleniumstore.activities

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_product_list.*
import org.json.JSONArray
import personal.pedrofigueiredo.milleniumstore.R
import personal.pedrofigueiredo.milleniumstore.adapters.ProductListAdapter
import personal.pedrofigueiredo.milleniumstore.data.Product
import java.lang.ref.WeakReference
import java.net.URL

class ProductListActivity : AppCompatActivity() {
    val PRODUCT_LIST_URL: String = "https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/products.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        GetProductTask(this, listView).execute(PRODUCT_LIST_URL)
    }

    class GetProductTask(activity: ProductListActivity, lView: ListView) : AsyncTask<String, Void, ArrayList<Product>>() {
        private val listViewReference: WeakReference<ListView>?
        private val actReference: WeakReference<AppCompatActivity>?

        init {
            listViewReference = WeakReference(lView)
            actReference = WeakReference(activity)
        }

        override fun doInBackground(vararg p0: String?): ArrayList<Product>? {
            val json: String = URL(p0[0]).readText()
            return parseProductsFromJSON(json)
        }

        override fun onPostExecute(result: ArrayList<Product>?) {
            if (listViewReference != null && actReference != null) {
                val list: ListView = listViewReference.get() as ListView
                val activity: AppCompatActivity = actReference.get() as ProductListActivity
                if (result != null) {
                    val adapter = ProductListAdapter(activity, result)
                    list.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }
        }

        fun parseProductsFromJSON(json: String): ArrayList<Product> {
            var products = ArrayList<Product>()

            val jsonArray: JSONArray = JSONArray(json)
            (0..(jsonArray.length() - 1))
                    .map { jsonArray.getJSONObject(it) }
                    .mapTo(products) {
                        Product(it.getString("title"), it.getInt("price"), it.getString("seller"),
                                it.getString("thumbnailHd"))
                    }



            return products
        }

    }

}
