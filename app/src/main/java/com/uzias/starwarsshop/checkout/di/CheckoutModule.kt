package com.uzias.starwarsshop.checkout.di

import android.arch.lifecycle.LifecycleOwner
import com.uzias.starwarsshop.checkout.presentation.navigation.CheckoutNavigation
import com.uzias.starwarsshop.checkout.presentation.navigation.CheckoutNavigationImpl
import com.uzias.starwarsshop.checkout.presentation.presenter.CheckoutPresenter
import com.uzias.starwarsshop.checkout.presentation.presenter.CheckoutPresenterImpl
import com.uzias.starwarsshop.checkout.presentation.view.CheckoutActivity
import com.uzias.starwarsshop.checkout.presentation.view.CheckoutView
import com.uzias.starwarsshop.core.application.di.IOScheduler
import com.uzias.starwarsshop.core.application.di.UIScheduler
import com.uzias.starwarsshop.core.behaviours.BehavioursCoordinator
import com.uzias.starwarsshop.core.behaviours.di.BehavioursModule
import com.uzias.starwarsshop.core.presentation.*
import com.uzias.starwarsshop.core.presentation.lifecycles.LifecycleStrategist
import com.uzias.starwarsshop.core.presentation.lifecycles.di.LifecycleStrategistModule
import com.uzias.starwarsshop.transactions.di.TransactionsUseCaseModule
import com.uzias.starwarsshop.transactions.domain.model.Transaction
import com.uzias.starwarsshop.transactions.domain.usecase.RegisterTransaction
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import kotlinx.android.synthetic.main.activity_checkout.*

@Module(includes = arrayOf(LifecycleStrategistModule::class, TransactionsUseCaseModule::class, BehavioursModule::class))
class CheckoutModule {

    @Provides
    fun providesCheckoutPresenter(view: CheckoutView, navigation: CheckoutNavigation,
                                  totalPrice: Double, registerTransaction: RegisterTransaction,
                                  coordinator: BehavioursCoordinator<Any>,
                                  @IOScheduler ioScheduler: Scheduler,
                                  @UIScheduler uiScheduler: Scheduler,
                                  strategist: LifecycleStrategist) : CheckoutPresenter
            = CheckoutPresenterImpl(view = view, navigation = navigation, totalPrice = totalPrice,
            registerTransaction = registerTransaction, coordinator = coordinator,
            ioScheduler =  ioScheduler, strategist = strategist, uiScheduler = uiScheduler)

    @Provides
    fun providesPlaceHolder(activity: CheckoutActivity): PlaceholderViewsManager =
            PlaceholderViewsManager(loadingViewStub = activity.container,
                    errorViewStub = activity.container,
                    emptyViewStub = activity.container,
                    containerView = activity.container)

    @Provides
    fun providesCheckoutView(activity: CheckoutActivity): CheckoutView = activity

    @Provides
    fun provideTotalPrice(activity: CheckoutActivity) : Double
            = activity.intent.getDoubleExtra(CheckoutActivity.EXTRA_TOTAL_PRICE, 0.0)

    @Provides
    fun providesCheckoutNavigation(activity: CheckoutActivity): CheckoutNavigation
            = CheckoutNavigationImpl(view = activity)


    @Provides fun providesEmptyStateView(view: CheckoutActivity): EmptyStateView = view

    @Provides fun providesLoadingView(view: CheckoutActivity): LoadingView = view

    @Provides fun providesErrorView(view: CheckoutActivity): ErrorStateView = view

    @Provides fun providesStrategist(view: CheckoutActivity): LifecycleOwner = view

    @Provides fun providesToogleRefreshView(view: CheckoutActivity): ToogleRefreshView = view

    @Provides fun providesNetworkingView(view: CheckoutActivity): NetworkingView = view
}