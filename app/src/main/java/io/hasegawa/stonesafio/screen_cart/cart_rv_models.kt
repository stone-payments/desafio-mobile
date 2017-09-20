package io.hasegawa.stonesafio.screen_cart

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.Typed3EpoxyController
import com.squareup.picasso.Picasso
import io.hasegawa.stonesafio.R
import io.hasegawa.stonesafio.common.EpoxyKotterHolder
import io.hasegawa.stonesafio.common.EpoxyModelWithHolderKt
import io.hasegawa.stonesafio.common.bindView
import io.hasegawa.stonesafio.domain.cart.CartProduct
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class CartRvProductModel(val product: CartProduct)
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
                        .fit()
                        .into(thumbnailIv)

                titleTv.text = title
                sellerTv.text = seller
                priceTv.text = "R\$$price" // TODO[hase] proper price formatting
            }
        }
    }

    class Holder : EpoxyKotterHolder() {
        val thumbnailIv: ImageView by bindView(R.id.cart_product_thumbnail_iv)
        val titleTv: TextView by bindView(R.id.cart_product_title_tv)
        val sellerTv: TextView by bindView(R.id.cart_product_seller_tv)
        val priceTv: TextView by bindView(R.id.cart_product_price_tv)
    }

    override fun createNewHolder(): Holder = Holder()
}

class CartRvBottomModel(val total: String, val canConfirm: Boolean, private val confirmClicksCb: () -> Unit)
    : EpoxyModelWithHolderKt<CartRvBottomModel.Holder>(
        R.layout.item_cart_bottom, "cart-bottom") {

    override fun bind(holder: Holder?) {
        holder?.apply {
            totalTv.text = total
            confirmBt.isEnabled = canConfirm
            confirmBt.setOnClickListener { confirmClicksCb() }
        }
    }

    override fun unbind(holder: Holder?) {
        holder?.confirmBt?.setOnClickListener(null)
    }

    class Holder : EpoxyKotterHolder() {
        val totalTv: TextView by bindView(R.id.cart_bottom_total_tv)
        val confirmBt: Button by bindView(R.id.cart_bottom_confirm_cart_bt)
    }

    override fun createNewHolder(): Holder = Holder()
}

class CartRvEmptyModel : EpoxyModelWithHolderKt<CartRvEmptyModel.Holder>(
        R.layout.item_cart_empty, "cart-empty-id") {
    override fun createNewHolder(): Holder = Holder()

    class Holder : EpoxyKotterHolder()
}

class CartRvController : Typed3EpoxyController<List<CartProduct>, String, Boolean>() {
    private val confirmClicksSubject = PublishSubject.create<Unit>()

    override fun buildModels(data: List<CartProduct>?, total: String, canConfirm: Boolean) {
        data
                ?.map { product -> CartRvProductModel(product) }
                ?.onEach { add(it) }

        if (data?.isEmpty() == true) {
            add(CartRvEmptyModel())
        } else {
            add(CartRvBottomModel(total, canConfirm) { confirmClicksSubject.onNext(Unit) })
        }
    }

    fun observeConfirmClicks(): Observable<Unit> = confirmClicksSubject
}
