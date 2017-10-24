package com.stone.starwarsstore.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stone.starwarsstore.R
import com.stone.starwarsstore.`interface`.OnProductAddedListener
import com.stone.starwarsstore.extension.formatPrice
import com.stone.starwarsstore.model.Product
import com.stone.starwarsstore.extension.load
import kotlinx.android.synthetic.main.row_product.view.*
import java.text.NumberFormat
import java.util.*

class ProductsAdapter(val mProducts: MutableList<Product>, val mListener: OnProductAddedListener, private val itemClick: (Product?) -> Unit) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.row_product, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = mProducts[position]
        holder.bind(product, itemClick)
    }

    fun modifyAmount(amount: String, increase: Boolean)
            = if (increase) (amount.toInt() + 1).toString() else (amount.toInt() - 1).toString()

    override fun getItemCount(): Int {
        return mProducts.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName = view.rowProduct_tvProductName
        val productImage = view.rowProduct_imProductImage
        val productPrice = view.rowProduct_tvProductPrice
        val productSeller = view.rowProduct_tvProductSeller
        val btPlus = view.rowProduct_btPlus
        val btMinus = view.rowProduct_btMinus
        val productAmount = view.rowProduct_tvAmount
        val btAddCart = view.rowProduct_btAddCart

        fun bind(product: Product?, itemClick: (Product?) -> Unit) {
            productName.text = product?.title
            productName.text = product?.title
            productSeller.text = product?.seller
            productImage.load(product?.thumbnailHd, { request -> request.fit() })

            btPlus.setOnClickListener {
                productAmount.setText(modifyAmount(productAmount.text.toString(), true))
            }

            btMinus.setOnClickListener {
                if (productAmount.text.toString().toInt() > 1)
                productAmount.setText(modifyAmount(productAmount.text.toString(), false))
            }

            btAddCart.setOnClickListener {
                product?.amount = productAmount.text.toString().toInt()
                mListener.onProductAddedToCart(product)
            }

            productPrice.text = product?.price?.formatPrice()

            itemView.setOnClickListener {
                itemClick(product) }
        }
    }

}