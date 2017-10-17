package com.stone.desafiomobile.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.stone.desafiomobile.R
import com.stone.desafiomobile.model.Product
import com.stone.desafiomobile.utils.formatPriceReal

/**
 * Adapter para popular a lista de produtos
 * @param mItemClickCalback callback chamado quando um botao do carrinho for pressionado
 * @param mSelectedItens itens que estao no carrinho
 */
class ProductsListAdapter(val mItemClickCalback: ItemClickCallback,
                          var mSelectedItens: MutableList<Product>) : RecyclerView.Adapter<ProductsListAdapter.ViewHolder>() {

    // itens para popular a lista
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
        val product = mValues[position]

        holder.mTitleView.text = product.title
        holder.mPriceView.text = product.price?.formatPriceReal()
        holder.mSellerView.text = product.seller

        // recupera a imagem atraves da url
        Glide.with(holder.mView).load(product.thumbnailHd).into(holder.mThumbnailView)

        // define a quantidade de itens que estao no carrinho
        val list = mSelectedItens.filter { it == product }
        holder.changeQuantity(list.size)

    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    /**
     * Classe que mantem as referencias do layout
     */
    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val mTitleView: TextView
        val mPriceView: TextView
        val mSellerView: TextView
        val mThumbnailView: ImageView
        val mQuantity: TextView
        val mAddCartButton: ImageButton
        val mRmCartButton: ImageButton

        init {
            mTitleView = mView.findViewById(R.id.title)
            mPriceView = mView.findViewById(R.id.price)
            mSellerView = mView.findViewById(R.id.seller)
            mThumbnailView = mView.findViewById(R.id.thumbnail)
            mQuantity = mView.findViewById(R.id.quantity_on_cart)

            mAddCartButton = mView.findViewById(R.id.add_to_cart_button)
            mRmCartButton = mView.findViewById(R.id.remove_from_cart_button)

            mAddCartButton.setOnClickListener({
                mItemClickCalback.addToCart(mValues[adapterPosition])
                notifyItemChanged(adapterPosition)
            })

            mRmCartButton.setOnClickListener({
                mItemClickCalback.removeFromCart(mValues[adapterPosition])
                notifyItemChanged(adapterPosition)
            })
        }

        /**
         * muda a quantidade do item no carrinho
         * @param quantity quantidade no carrinho
         */
        fun changeQuantity(quantity: Int) {
            mQuantity.text = quantity.toString()

            //mostra ou esconde o botao de remover
            if (quantity > 0) {
                mRmCartButton.visibility = View.VISIBLE
            } else {
                mRmCartButton.visibility = View.INVISIBLE
            }
        }
    }

    /**
     * Callback para lidar com eventos quando um botao do carrinho for pressionado
     */
    interface ItemClickCallback {

        fun addToCart(product: Product)

        fun removeFromCart(product: Product)

    }
}
