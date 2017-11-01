package com.uzias.starwarsshop.products.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.uzias.starwarsshop.R
import com.uzias.starwarsshop.products.presentation.model.PresentationProduct
import kotlinx.android.synthetic.main.list_item_product.view.*

class ProductsAdapter: RecyclerView.Adapter<ProductViewHolder>() {
    private val products = mutableListOf<PresentationProduct>()
    private lateinit var listener: ProductsListener

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.itemView.textViewName.text = product.title
        holder.itemView.textViewSeller.text = holder.itemView.context.getString(R.string.label_seller, product.seller)
        holder.itemView.textViewPrice.text = holder.itemView.context.getString(R.string.label_price, product.getPriceFormatted())
        holder.itemView.imageButtonAdd.setOnClickListener { listener.clickedButtonAdd(position) }
        holder.itemView.imageButtonRemove.setOnClickListener { listener.clickedButtonRemove(position) }

        holder.itemView.textViewCountItem.text = product.totalItem.toString()
        holder.itemView.textViewCountItem.visibility = if (product.totalItem != 0) View.VISIBLE else View.GONE
        holder.itemView.imageButtonRemove.visibility = if (product.totalItem != 0) View.VISIBLE else View.GONE
        Glide.with(holder.itemView).load(product.pictureUrl).into(holder.itemView.imageViewProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder
            = ProductViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_product, parent, false))

    fun setProducts(list: List<PresentationProduct>){
        products.removeAll(products)
        products.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        products.removeAll(products)
        notifyDataSetChanged()
    }

    fun setListener(listener: ProductsListener) {
        this.listener = listener
    }

    override fun getItemCount(): Int = products.size

}

class ProductViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

interface ProductsListener {
    fun clickedButtonAdd(position: Int)
    fun clickedButtonRemove(position: Int)
}
