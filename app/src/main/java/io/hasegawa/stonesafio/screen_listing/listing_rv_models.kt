package io.hasegawa.stonesafio.screen_listing

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.TypedEpoxyController
import com.squareup.picasso.Picasso
import io.hasegawa.presentation.screen_listing.ListingContract.Product
import io.hasegawa.stonesafio.R
import io.hasegawa.stonesafio.common.EpoxyKotterHolder
import io.hasegawa.stonesafio.common.EpoxyModelWithHolderKt
import io.hasegawa.stonesafio.common.bindView
import io.hasegawa.stonesafio.domain.common.log.logi
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ListingRvProductModel(val product: Product, private val buyClicksCb: (Product) -> Unit)
    : EpoxyModelWithHolderKt<ListingRvProductModel.Holder>(
        R.layout.item_listing_product, product.id) {

    override fun equals(other: Any?): Boolean =
            product == (other as? ListingRvProductModel)?.product

    override fun bind(holder: Holder?) {
        holder?.apply {
            product.run {
                Picasso.with(holder.titleTv.context)
                        .load(thumbnailUrl)
                        .into(thumbnailIv)

                titleTv.text = title
                priceTv.text = price
                sellerTv.text = seller

                val showBuyBt = !inCart

                buyBt.isEnabled = showBuyBt
                buyBt.visibility = when (showBuyBt) {
                    true -> View.VISIBLE
                    else -> View.INVISIBLE
                }

                buyBt.setOnClickListener { buyClicksCb(product) }
            }
        }
    }

    override fun unbind(holder: Holder?) {
        holder?.buyBt?.setOnClickListener(null)
    }

    class Holder : EpoxyKotterHolder() {
        val thumbnailIv: ImageView by bindView(R.id.listing_product_thumbnail_iv)
        val titleTv: TextView by bindView(R.id.listing_product_title_tv)
        val priceTv: TextView by bindView(R.id.listing_product_price_tv)
        val sellerTv: TextView by bindView(R.id.listing_product_seller_tv)
        val buyBt: Button by bindView(R.id.listing_product_buy_bt)
    }

    override fun createNewHolder(): Holder = Holder()
}


class ListingRvController : TypedEpoxyController<List<Product>>() {
    private val buyClicksSubject = PublishSubject.create<Product>()

    override fun buildModels(data: List<Product>?) {
        data
                ?.map { product -> ListingRvProductModel(product) { buyClicksSubject.onNext(it) } }
                ?.onEach { add(it) }
    }

    fun observeBuyClicks(): Observable<Product> = buyClicksSubject
}