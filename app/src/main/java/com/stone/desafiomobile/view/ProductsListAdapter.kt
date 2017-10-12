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


class ProductsListAdapter(val mItemClickCalback: ItemClickCallback) : RecyclerView.Adapter<ProductsListAdapter.ViewHolder>() {

    var mValues: List<Product> = ArrayList<Product>()
        set (new) {
            if (new != field) {
                field = new
                notifyDataSetChanged()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = mValues.get(position)

        holder.mTitleView.text = product.title
        holder.mPriceView.text = product.price?.formatPriceReal()
        holder.mSellerView.text = product.seller

        Glide.with(holder.mView).load(product.thumbnailHd).into(holder.mThumbnailView);
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val mTitleView: TextView
        val mPriceView: TextView
        val mSellerView: TextView
        val mThumbnailView: ImageView
        val mBuyButton: Button
        val mAddCartButton: Button
        val mRmCartButton: Button

        init {
            mTitleView = mView.findViewById(R.id.title)
            mPriceView = mView.findViewById(R.id.price)
            mSellerView = mView.findViewById(R.id.seller)
            mThumbnailView = mView.findViewById(R.id.thumbnail)

            mBuyButton = mView.findViewById(R.id.buy_button)
            mBuyButton.setOnClickListener { mItemClickCalback.buyProduct() }

            mAddCartButton = mView.findViewById(R.id.add_to_cart_button)
            mRmCartButton = mView.findViewById(R.id.remove_from_cart_button)

            mAddCartButton.setOnClickListener({
                mRmCartButton.visibility = View.VISIBLE
                mAddCartButton.visibility = View.GONE
                mItemClickCalback.addToCart(mValues.get(adapterPosition))
            })

            mRmCartButton.setOnClickListener({
                mAddCartButton.visibility = View.VISIBLE
                mRmCartButton.visibility = View.GONE
                mItemClickCalback.removeFromCart(mValues.get(adapterPosition))
            })
        }
    }

    interface ItemClickCallback {
        fun buyProduct()

        fun addToCart(product: Product)

        fun removeFromCart(product: Product)

    }
}
