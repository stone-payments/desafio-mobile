package io.hasegawa.stonesafio.screen_listing

import io.hasegawa.presentation.screen_listing.ListingContract
import io.hasegawa.stonesafio.domain.common.log.logi

class ListingNavigator(val controller: ListingController) : ListingContract.Navigator {
    override fun goToCart() {
        logi { "GoToCart NoOp" }
    }

    override fun goToTransactions() {
        logi { "GoToTransactions NoOp" }
    }
}