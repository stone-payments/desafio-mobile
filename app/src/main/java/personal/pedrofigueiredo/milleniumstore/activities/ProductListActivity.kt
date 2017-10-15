package personal.pedrofigueiredo.milleniumstore.activities

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_product_list.*
import org.jetbrains.anko.alert
import org.json.JSONArray
import personal.pedrofigueiredo.milleniumstore.R
import personal.pedrofigueiredo.milleniumstore.adapters.ProductListAdapter
import personal.pedrofigueiredo.milleniumstore.common.GlobalApplication
import personal.pedrofigueiredo.milleniumstore.data.Product
import java.lang.ref.WeakReference
import java.net.URL

class ProductListActivity : AppCompatActivity() {
    private val PRODUCT_LIST_URL: String = "https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/products.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        GetProductTask(this, listView).execute(PRODUCT_LIST_URL)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.viewCart -> {
                goToCart()
                true
            }
            R.id.viewOrders -> {
                viewOrders()
                true
            }
            else -> false
        }
    }

    private fun viewOrders() {
        startActivity(Intent(this, OrderListActivity::class.java))
    }

    private fun goToCart() {
        val app = application as GlobalApplication
        val cart = app.getShoppingCart()
        when(cart?.isEmpty()){
            true -> {
                alert(getString(R.string.dialog_cart_empty_message)){
                    title = getString(R.string.dialog_cart_empty_title)
                    positiveButton("OK"){}
                }.show()
            }
            false -> {
                intent = Intent(this, ViewCartActivity::class.java)
                startActivity(intent)
            }
        }

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

                    list.onItemClickListener = OnItemClickListener { _: AdapterView<*>?, _: View?,
                                                                     position: Int, _: Long ->
                        val product: Product = adapter.getItem(position)
                        val intent: Intent = ProductDetailActivity.newIntent(activity, product)
                        activity.startActivity(intent)
                    }
                }
            }
        }

        private fun parseProductsFromJSON(json: String): ArrayList<Product> {
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
