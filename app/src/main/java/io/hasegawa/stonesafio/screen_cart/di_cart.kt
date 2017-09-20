package io.hasegawa.stonesafio.screen_cart

import dagger.Component
import dagger.Module
import dagger.Provides
import io.hasegawa.presentation.screen_cart.CartCCValidationUC
import io.hasegawa.presentation.screen_cart.CartPayUC
import io.hasegawa.presentation.screen_cart.CartPresenter
import io.hasegawa.presentation.screen_cart.CartProductsUC
import io.hasegawa.presentation.screen_cart.CartRemoveItemUC
import io.hasegawa.stonesafio.di.BaseDIComponent
import io.hasegawa.stonesafio.domain.cart.CartClearUC
import io.hasegawa.stonesafio.domain.cart.CartGetListUC
import io.hasegawa.stonesafio.domain.cart.CartRepository
import io.hasegawa.stonesafio.domain.cc.CCValidatorDevice
import io.hasegawa.stonesafio.domain.payment.PaymentPayUC
import io.hasegawa.stonesafio.domain.payment.PaymentService
import io.hasegawa.stonesafio.domain.payment.TransactionRepository
import javax.inject.Scope


@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CartControllerScope

@Module
class CartDIModule(private val controller: CartController) {
    @Provides
    @CartControllerScope
    fun providePresenter(cartRepository: CartRepository, ccValidatorDevice: CCValidatorDevice,
                         paymentService: PaymentService, transactionRepository: TransactionRepository)
            : CartPresenter {
        val navigator = CartNavigator(controller)
        val getListUC = CartGetListUC(cartRepository)
        val cartClearUC = CartClearUC(cartRepository)
        val paymentUC = PaymentPayUC(paymentService, transactionRepository)

        val productsUC = CartProductsUC(getListUC)
        val removeItemUC = CartRemoveItemUC(cartRepository)

        val ccValidationUC = CartCCValidationUC(ccValidatorDevice)


        val payUC = CartPayUC(paymentUC, cartClearUC)

        return CartPresenter(navigator, productsUC, removeItemUC, ccValidationUC, payUC)
    }
}

@CartControllerScope
@Component(modules = arrayOf(CartDIModule::class),
        dependencies = arrayOf(BaseDIComponent::class))
interface CartDIComponent {
    companion object {
        fun initialize(controller: CartController): CartDIComponent {
            return DaggerCartDIComponent.builder()
                    .cartDIModule(CartDIModule(controller))
                    .baseDIComponent(BaseDIComponent.instance)
                    .build()
        }
    }

    fun getPresenter(): CartPresenter
}
