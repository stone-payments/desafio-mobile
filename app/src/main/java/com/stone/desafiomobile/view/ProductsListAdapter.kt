package com.stone.desafiomobile.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.stone.desafiomobile.R
import com.stone.desafiomobile.model.Product
import com.stone.desafiomobile.utils.formatPriceReal


class ProductsListAdapter(val mItemClickCalback: ItemClickCallback,
                          var selectedItens: List<Product>) : RecyclerView.Adapter<ProductsListAdapter.ViewHolder>() {

    var mValues: List<Product> = ArrayList()
        set (new) {
            if (new != field) {
                field = new
                notifyDataSetChanged()
            }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_product_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = mValues.get(position)

        holder.mTitleView.text = product.title
        holder.mPriceView.text = product.price?.formatPriceReal()
        holder.mSellerView.text = product.seller

        Glide.with(holder.mView).load(product.thumbnailHd).into(holder.mThumbnailView);

        if (selectedItens.contains(product)) {
            holder.showButtonRmCart()
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val mTitleView: TextView
        val mPriceView: TextView
        val mSellerView: TextView
        val mThumbnailView: ImageView
        val mAddCartButton: Button
        val mRmCartButton: Button

        init {
            mTitleView = mView.findViewById(R.id.title)
            mPriceView = mView.findViewById(R.id.price)
            mSellerView = mView.findViewById(R.id.seller)
            mThumbnailView = mView.findViewById(R.id.thumbnail)

            mAddCartButton = mView.findViewById(R.id.add_to_cart_button)
            mRmCartButton = mView.findViewById(R.id.remove_from_cart_button)

            mAddCartButton.setOnClickListener({
                switchCartButtons()
                mItemClickCalback.addToCart(mValues.get(adapterPosition))
            })

            mRmCartButton.setOnClickListener({
                switchCartButtons()
                mItemClickCalback.removeFromCart(mValues.get(adapterPosition))
            })
        }

        fun switchCartButtons() {
            val aux = mRmCartButton.visibility
            mRmCartButton.visibility = mAddCartButton.visibility
            mAddCartButton.visibility = aux
        }

        fun showButtonRmCart() {
            mRmCartButton.visibility = View.VISIBLE
            mAddCartButton.visibility = View.GONE
        }
    }

    interface ItemClickCallback {

        fun addToCart(product: Product)

        fun removeFromCart(product: Product)

    }
}
