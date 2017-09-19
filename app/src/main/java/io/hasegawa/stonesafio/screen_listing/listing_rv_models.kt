package io.hasegawa.stonesafio.screen_listing

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.Typed3EpoxyController
import com.squareup.picasso.Picasso
import io.hasegawa.presentation.screen_listing.ListingContract.ListingErrorType
import io.hasegawa.presentation.screen_listing.ListingContract.Product
import io.hasegawa.stonesafio.R
import io.hasegawa.stonesafio.common.EpoxyKotterHolder
import io.hasegawa.stonesafio.common.EpoxyModelWithHolderKt
import io.hasegawa.stonesafio.common.bindView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ListingRvProductModel(val product: Product, private val buyClicksCb: (Product) -> Unit)
    : EpoxyModelWithHolderKt<ListingRvProductModel.Holder>(
        R.layout.item_listing_product, product.id) {

    override fun equals(other: Any?): Boolean =
            product == (other as? ListingRvProductModel)?.product

    override fun hashCode(): Int = product.id.hashCode()

    override fun bind(holder: Holder?) {
        holder?.apply {
            product.run {
                Picasso.with(holder.titleTv.context)
                        .load(thumbnailUrl)
                        .into(thumbnailIv)

                titleTv.text = title
                priceTv.text = "R$ $price" // TODO[hase] proper price formatting
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

class ListingRvConnIssueModel : EpoxyModelWithHolderKt<ListingRvConnIssueModel.Holder>(
        R.layout.item_listing_conn_issue, "conn-issue-id") {
    override fun createNewHolder(): Holder = Holder()

    class Holder : EpoxyKotterHolder()
}


class ListingRvUnknownIssueModel : EpoxyModelWithHolderKt<ListingRvUnknownIssueModel.Holder>(
        R.layout.item_listing_unknown_issue, "unknown-issue-id") {
    override fun createNewHolder(): Holder = Holder()

    class Holder : EpoxyKotterHolder()
}

class ListingRvLoadingModel : EpoxyModelWithHolderKt<ListingRvLoadingModel.Holder>(
        R.layout.item_listing_loading, "loading-id") {
    override fun createNewHolder(): Holder = Holder()

    class Holder : EpoxyKotterHolder()
}


class ListingRvController : Typed3EpoxyController<Boolean, ListingErrorType, List<Product>>() {
    private val buyClicksSubject = PublishSubject.create<Product>()

    override fun buildModels(loading: Boolean?, errorType: ListingErrorType?, list: List<Product>?) {
        when {
            loading == true -> add(ListingRvLoadingModel())
            errorType == ListingErrorType.ConnectionIssue -> add(ListingRvConnIssueModel())
            errorType == ListingErrorType.Unknown -> add(ListingRvUnknownIssueModel())
            else -> list
                    ?.map { product -> ListingRvProductModel(product) { buyClicksSubject.onNext(it) } }
                    ?.onEach { add(it) }
        }
    }

    fun observeBuyClicks(): Observable<Product> = buyClicksSubject
}
