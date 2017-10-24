package com.stone.starwarsstore.ui.adapter

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stone.starwarsstore.Cart
import com.stone.starwarsstore.R
import com.stone.starwarsstore.`interface`.OnProductAmountChangedListener
import com.stone.starwarsstore.extension.formatPrice
import com.stone.starwarsstore.extension.load
import com.stone.starwarsstore.model.Product
import kotlinx.android.synthetic.main.row_cart_product.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

class CartProductsAdapter (val mProducts: MutableList<Product?>?, val onProductAmountChangedListener: OnProductAmountChangedListener) : RecyclerView.Adapter<CartProductsAdapter.CartProductViewHolder>() {

    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CartProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        context = parent?.context
        return CartProductViewHolder(layoutInflater.inflate(R.layout.row_cart_product, parent, false))
    }

    override fun onBindViewHolder(holder: CartProductsAdapter.CartProductViewHolder, position: Int) {
        holder.bind(mProducts?.get(position), position)
    }

    override fun getItemCount(): Int {
        return mProducts!!.size
    }

    fun modifyAmount(amount: String, increase: Boolean)
            = if (increase) { (amount.toInt() + 1).toString()}  else (amount.toInt() - 1).toString()

    fun removeItem(product: Product?) {
        mProducts?.remove(product)
        notifyDataSetChanged()
        Cart.removeProduct(product)
        onProductAmountChangedListener.onProductAmountChanged()
        context?.toast(context?.getString(R.string.toast_removed_item)!!)
    }

    inner class CartProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName = view.rowCartProduct_tvProductTitle
        val productPrice = view.rowCartProduct_tvProductPrice
        val productImage = view.rowCartProduct_imProductImage
        val productAmount = view.rowCartProduct_tvProductAmount
        val btMinus = view.rowCartProduct_btMinus
        val btPlus = view.rowCartProduct_btPlus
        val productRemove = view.rowCartProduct_btRemove

        fun bind(product: Product?, position: Int) {
            productName.text = product?.title
            productImage.load(product?.thumbnailHd, { request -> request.fit() })

            productPrice.text = (product?.price!! * product.amount!!).formatPrice()
            productAmount.setText(product.amount.toString())

            btMinus.setOnClickListener {
                if (productAmount.text.toString().toInt() == 1) {
                    showRemoveProductAlert(product)
                } else if (productAmount.text.toString().toInt() > 1) {
                    productAmount.setText(modifyAmount(productAmount.text.toString(), false))
                    productPrice.text = (product.price!! * productAmount.text.toString().toInt()).formatPrice()
                    Cart.changeProductAmount(position, productAmount.text.toString().toInt())
                    onProductAmountChangedListener.onProductAmountChanged()
                }
            }

            btPlus.setOnClickListener {
                productAmount.setText(modifyAmount(productAmount.text.toString(), true))
                productPrice.text = (product.price!! * productAmount.text.toString().toInt()).formatPrice()
                Cart.changeProductAmount(position, productAmount.text.toString().toInt())
                onProductAmountChangedListener.onProductAmountChanged()
            }

            productRemove.setOnClickListener {
                showRemoveProductAlert(product)
            }

        }
        fun showRemoveProductAlert(product: Product?){

            context?.alert(context?.getString(R.string.alert_remove_product_message)!!, context?.getString(R.string.alert_remove_product_title)!!) {
                yesButton { removeItem(product) }
                noButton { dialog: DialogInterface -> dialog.cancel() }
            }?.show()

        }
    }
}