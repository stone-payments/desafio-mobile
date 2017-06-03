package com.partiufast.mercadodoimperador.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.partiufast.mercadodoimperador.model.Product
import com.partiufast.mercadodoimperador.R
import kotlinx.android.synthetic.main.cart_card.view.*


class CartListAdapter(val products: List<Product>) :
        RecyclerView.Adapter<CartListAdapter.CartItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_card, parent, false)
        return CartItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.bindProduct(products[position])
    }

    override fun getItemCount(): Int = products.size

    class CartItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindProduct(product: Product) {
            with(product) {
                itemView.product_name_to_buy_text_view.text = product.title
                itemView.thumbnail_to_buy_drawee_view.setImageURI(product.thumbnailHd)
                if (product.productCount > 1)
                    itemView.product_price_text_view.text = product.productCount.toString() + " items"
                else
                    itemView.product_price_text_view.text = product.productCount.toString() + " item"
            }
        }
    }
}