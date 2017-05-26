package com.partiufast.mercadodoimperador.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.drawee.view.SimpleDraweeView
import com.partiufast.mercadodoimperador.Product
import com.partiufast.mercadodoimperador.ProductFragmentCallback
import com.partiufast.mercadodoimperador.R
import kotlinx.android.synthetic.main.product_card.view.*
import java.util.*

class AvailableListAdapter(private val products: ArrayList<Product>) : RecyclerView.Adapter<AvailableListAdapter.ProductViewHolder>() {
    private var mContext: Context? = null
    private var productFragmentCallback: ProductFragmentCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false)
        productFragmentCallback = parent.context as ProductFragmentCallback
        return ProductViewHolder(view, OnClickProductListener(productFragmentCallback!!))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindProduct(products[position], position)

    }

    override fun getItemCount(): Int = products.size

    class ProductViewHolder(view: View, val onClickProductListener: OnClickProductListener) : RecyclerView.ViewHolder(view) {
        fun bindProduct(product: Product, position: Int) {
            with(product) {
                onClickProductListener.updatePosition(position)
                onClickProductListener.updateView(itemView.thumbnail_drawee_view)
                itemView.product_price_text_view.text = "R$" + product.price.toString()
                itemView.product_name_text_view.text = product.title
                itemView.thumbnail_drawee_view.setImageURI(product.thumbnailHd)
                itemView.setOnClickListener(onClickProductListener)
            }
        }
    }

    inner class OnClickProductListener(val productFragmentCallback: ProductFragmentCallback) : View.OnClickListener {
        private var position: Int? = null
        private var thumbnail_drawee_view: SimpleDraweeView? = null

        override fun onClick(v: View?) {
            productFragmentCallback.onClickProductCard(position!!, thumbnail_drawee_view!!)
        }

        fun updatePosition(position: Int) {
            this.position = position
        }

        fun updateView(thumbnail_drawee_view: SimpleDraweeView?) {
            this.thumbnail_drawee_view = thumbnail_drawee_view
        }
    }
}





