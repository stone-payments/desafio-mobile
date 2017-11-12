package douglasspgyn.com.github.desafiostone.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import douglasspgyn.com.github.desafiostone.R
import douglasspgyn.com.github.desafiostone.business.model.Product
import douglasspgyn.com.github.desafiostone.common.extensions.inflate
import douglasspgyn.com.github.desafiostone.common.extensions.loadUrl
import douglasspgyn.com.github.desafiostone.common.extensions.toCurrency
import kotlinx.android.synthetic.main.item_product.view.*


/**
 * Created by Douglas on 12/11/17.
 */

class ProductAdapter(private val products: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ProductViewHolder {
        return ProductViewHolder(parent?.inflate(R.layout.item_product))
    }

    override fun onBindViewHolder(holder: ProductViewHolder?, position: Int) {
        holder?.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }


    class ProductViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(product: Product) {
            with(itemView) {
                productPhoto.loadUrl(product.thumbnailHd, R.drawable.ic_darth_vader)
                productTitle.text = product.title
                productPrice.text = product.price.toCurrency()
                productSeller.text = context.getString(R.string.sold_by, product.seller)
            }
        }
    }
}