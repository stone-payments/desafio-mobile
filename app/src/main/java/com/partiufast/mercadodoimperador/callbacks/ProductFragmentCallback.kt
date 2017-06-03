package com.partiufast.mercadodoimperador.callbacks

import com.facebook.drawee.view.SimpleDraweeView

interface ProductFragmentCallback {
    fun onClickProductCard(position: Int, thumbnail_drawee_view: SimpleDraweeView)
}