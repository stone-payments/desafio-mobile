package io.hasegawa.stonesafio.screen_listing

import dagger.Component
import dagger.Module
import dagger.Provides
import io.hasegawa.presentation.screen_listing.ListingAddToCartUC
import io.hasegawa.presentation.screen_listing.ListingFetcherUC
import io.hasegawa.presentation.screen_listing.ListingPresenter
import io.hasegawa.stonesafio.di.BaseDIComponent
import io.hasegawa.stonesafio.domain.cart.CartAddUC
import io.hasegawa.stonesafio.domain.cart.CartGetListUC
import io.hasegawa.stonesafio.domain.cart.CartRepository
import io.hasegawa.stonesafio.domain.listing.ListItemsUC
import io.hasegawa.stonesafio.domain.listing.ListingService
import javax.inject.Scope


@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ListingControllerScope

@Module
class ListingDIModule(private val controller: ListingController) {
    @Provides
    @ListingControllerScope
    fun providePresenter(service: ListingService, cartRepository: CartRepository): ListingPresenter {
        val cartGetUC = CartGetListUC(cartRepository)
        val listUC = ListItemsUC(service)
        val cartAddUC = CartAddUC(listUC, cartRepository)

        val navigator = ListingNavigator(controller)

        val fetcherUC = ListingFetcherUC(listUC, cartGetUC)
        val addToCartUC = ListingAddToCartUC(cartAddUC, cartGetUC)

        return ListingPresenter(navigator, fetcherUC, addToCartUC)
    }
}

@ListingControllerScope
@Component(modules = arrayOf(ListingDIModule::class),
        dependencies = arrayOf(BaseDIComponent::class))
interface ListingDIComponent {
    companion object {
        fun initialize(controller: ListingController): ListingDIComponent {
            return DaggerListingDIComponent.builder()
                    .baseDIComponent(BaseDIComponent.instance)
                    .listingDIModule(ListingDIModule(controller))
                    .build()
        }
    }

    fun getPresenter(): ListingPresenter
}