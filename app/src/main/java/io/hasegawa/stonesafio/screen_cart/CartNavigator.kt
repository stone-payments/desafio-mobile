package io.hasegawa.stonesafio.screen_cart

import io.hasegawa.presentation.screen_cart.CartContract
import io.hasegawa.stonesafio.domain.common.log.logd

class CartNavigator(val controller: CartController) : CartContract.Navigator {
    override fun goBackToStore() {
        logd { "Going back to store" }
        controller.router.popCurrentController()
    }
}