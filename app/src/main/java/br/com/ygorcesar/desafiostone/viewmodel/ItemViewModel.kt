package br.com.ygorcesar.desafiostone.viewmodel

import android.databinding.BaseObservable
import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import br.com.ygorcesar.desafiostone.R
import br.com.ygorcesar.desafiostone.data.ShoppingCart
import br.com.ygorcesar.desafiostone.data.loadImage
import br.com.ygorcesar.desafiostone.data.toCurrencyBRL
import br.com.ygorcesar.desafiostone.data.toast
import br.com.ygorcesar.desafiostone.model.Item

@BindingAdapter("bindImage")
fun bindThumbnail(iv: ImageView, url: String) {
    iv.loadImage(url)
}

class ItemViewModel(private var item: Item, val onItemClick: (item: Item) -> Unit = {}) : BaseObservable() {

    fun setItem(item: Item) {
        this.item = item
        notifyChange()
    }

    fun getItem() = this.item

    fun getTitle() = item.title

    fun getSeller() = item.seller

    fun getPrice() = item.price.toCurrencyBRL()

    fun getThumbnailUrl() = item.thumbnailHd

    fun bindViewClick(): View.OnClickListener {
        return View.OnClickListener { onItemClick(item) }
    }

    fun addItem() = ShoppingCart.Companion.instance.addItem(item)

    fun removeItem() = ShoppingCart.Companion.instance.items.remove(item)

    fun removeItemFromCart(): View.OnClickListener {
        return View.OnClickListener {
            removeItem()
            onItemClick(item)
        }
    }

    fun addItemToCart(): View.OnClickListener {
        return View.OnClickListener { v ->
            addItem()
            v.context.toast(R.string.item_added)
            onItemClick(item)
        }
    }
}