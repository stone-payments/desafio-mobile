package com.partiufast.mercadodowatto

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.product_card.view.*

class ProductListAdapter (val products: List<Product>) :
        RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false)
        return ProductViewHolder(view)
    }

    override  fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindProduct(products[position])
    }

    override fun getItemCount(): Int = products.size

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun  bindProduct(product: Product) {
            with(product){
                itemView.product_price_text_view.text = "R$" + product.price.toString()
                itemView.product_name_text_view.text = product.title
                itemView.thumbnail_drawee_view.setImageURI(product.thumbnailLink)
            }

        }

    }
}