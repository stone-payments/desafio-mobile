package io.hasegawa.stonesafio.screen_cart

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.TypedEpoxyController
import com.squareup.picasso.Picasso
import io.hasegawa.stonesafio.R
import io.hasegawa.stonesafio.common.EpoxyKotterHolder
import io.hasegawa.stonesafio.common.EpoxyModelWithHolderKt
import io.hasegawa.stonesafio.common.bindView
import io.hasegawa.stonesafio.domain.cart.CartProduct
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class CartRvProductModel(val product: CartProduct, private val removeClicksCb: (CartProduct) -> Unit)
    : EpoxyModelWithHolderKt<CartRvProductModel.Holder>(
        R.layout.item_cart_product, product.id) {

    override fun equals(other: Any?): Boolean =
            product == (other as? CartRvProductModel)?.product

    override fun hashCode(): Int = product.id.hashCode()

    override fun bind(holder: Holder?) {
        holder?.apply {
            product.run {
                Picasso.with(holder.titleTv.context)
                        .load(thumbnailUrl)
                        .into(thumbnailIv)

                titleTv.text = title
                priceTv.text = "R$ $price" // TODO[hase] proper price formatting

                removeBt.setOnClickListener { removeClicksCb(product) }
            }
        }
    }

    override fun unbind(holder: Holder?) {
        holder?.removeBt?.setOnClickListener(null)
    }

    class Holder : EpoxyKotterHolder() {
        val thumbnailIv: ImageView by bindView(R.id.cart_product_thumbnail_iv)
        val titleTv: TextView by bindView(R.id.cart_product_title_tv)
        val priceTv: TextView by bindView(R.id.cart_product_price_tv)
        val removeBt: Button by bindView(R.id.cart_product_remove_bt)
    }

    override fun createNewHolder(): Holder = Holder()
}


class CartRvController : TypedEpoxyController<List<CartProduct>>() {
    private val removeClicksSubject = PublishSubject.create<CartProduct>()

    override fun buildModels(data: List<CartProduct>?) {
        data
                ?.map { product -> CartRvProductModel(product) { removeClicksSubject.onNext(it) } }
                ?.onEach { add(it) }
    }

    fun observeRemoveClicks(): Observable<CartProduct> = removeClicksSubject
}
