package douglasspgyn.com.github.desafiostone.ui.cart.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import douglasspgyn.com.github.desafiostone.R
import douglasspgyn.com.github.desafiostone.application.App.Companion.productDao
import douglasspgyn.com.github.desafiostone.application.Constants
import douglasspgyn.com.github.desafiostone.business.model.Product
import douglasspgyn.com.github.desafiostone.common.extensions.inflate
import douglasspgyn.com.github.desafiostone.common.extensions.loadUrl
import douglasspgyn.com.github.desafiostone.common.extensions.toCurrency
import douglasspgyn.com.github.desafiostone.ui.cart.CartContract
import douglasspgyn.com.github.desafiostone.ui.product.ProductActivity
import kotlinx.android.synthetic.main.item_cart.view.*

/**
 * Created by Douglas on 13/11/17.
 */

class CartAdapter(val products: MutableList<Product>, val presenter: CartContract.Presenter) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CartViewHolder {
        return CartViewHolder(parent?.inflate(R.layout.item_cart), this)
    }

    override fun onBindViewHolder(holder: CartViewHolder?, position: Int) {
        holder?.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    private fun removeProduct(position: Int) {
        products.remove(products[position])
        notifyItemRemoved(position)
    }

    private fun updateTotalPrices() {
        presenter.updateViewData()
    }

    class CartViewHolder(itemView: View?, private val adapter: CartAdapter) : RecyclerView.ViewHolder(itemView) {

        fun bind(product: Product) {
            with(itemView) {
                productPhoto.loadUrl(product.thumbnailHd, R.drawable.ic_darth_vader)
                productTitle.text = product.title
                productPrice.text = product.price.toCurrency()
                productQuantity.text = product.quantity.toString()
                productTotal.text = context.getString(R.string.total_price,
                        (product.price * product.quantity).toCurrency())

                productRemove.setOnClickListener {
                    removeProductDialog(context, product)
                }

                removeQuantity.setOnClickListener {
                    removeQuantity(product)
                }

                addQuantity.setOnClickListener {
                    addQuantity(product)
                }

                setOnClickListener {
                    context.startActivity(Intent(context, ProductActivity::class.java).apply {
                        putExtra(Constants.PRODUCT, product)
                    })
                }
            }
        }

        private fun addQuantity(product: Product) {
            with(itemView) {
                product.quantity += 1
                productDao?.updateProduct(product)
                updateProductView(product)
            }
        }

        private fun removeQuantity(product: Product) {
            with(itemView) {
                if (product.quantity == 1) {
                    removeProductDialog(context, product)
                } else {
                    product.quantity -= 1
                    productDao?.updateProduct(product)
                    updateProductView(product)
                }
            }
        }

        private fun updateProductView(product: Product) {
            with(itemView) {
                productQuantity.text = product.quantity.toString()
                productTotal.text = context.getString(R.string.total_price,
                        (product.price * product.quantity).toCurrency())

                adapter.updateTotalPrices()
            }
        }

        private fun removeProductDialog(context: Context, product: Product) {
            AlertDialog.Builder(context).create().apply {
                setTitle(context.getString(R.string.remove_product))
                setMessage(context.getString(R.string.remove_product_message))
                setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.remove), { _, _ ->
                    productDao?.deleteProduct(product)
                    adapter.removeProduct(adapterPosition)
                    adapter.updateTotalPrices()
                })
                setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(R.string.cancel), { _, _ ->
                    dismiss()
                })
                show()
            }
        }
    }
}