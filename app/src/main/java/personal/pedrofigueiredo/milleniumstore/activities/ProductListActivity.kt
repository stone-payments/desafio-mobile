package personal.pedrofigueiredo.milleniumstore.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import personal.pedrofigueiredo.milleniumstore.R
import kotlinx.android.synthetic.main.activity_product_list.*
import personal.pedrofigueiredo.milleniumstore.adapters.ProductListAdapter
import personal.pedrofigueiredo.milleniumstore.data.Product

class ProductListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        val adapter: ProductListAdapter = ProductListAdapter(this, getProductData())
        listView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    // sample data to test the list view
    // when fetching data from the Network should be done in a background thread
    fun getProductData(): ArrayList<Product> {
        var result = ArrayList<Product>()

        val p1 = Product("Blusa do Império", 7999, "Joao da Silva", "https://cdn.awsli.com.br/600x450/21/21351/produto/3853007/f66e8c63ab.jpg")
        result.add(p1)

        val p2 = Product("Blusa Han Shot First", 7990, "Joana", "https://cdn.awsli.com.br/1000x1000/21/21351/produto/7234148/55692a941d.jpg")
        result.add(p2)

        return result
    }
}
