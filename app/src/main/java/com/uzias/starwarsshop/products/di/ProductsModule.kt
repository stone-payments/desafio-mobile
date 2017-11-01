package com.uzias.starwarsshop.products.di

import android.arch.lifecycle.LifecycleOwner
import com.uzias.starwarsshop.core.application.di.IOScheduler
import com.uzias.starwarsshop.core.application.di.UIScheduler
import com.uzias.starwarsshop.core.behaviours.BehavioursCoordinator
import com.uzias.starwarsshop.core.behaviours.di.BehavioursModule
import com.uzias.starwarsshop.core.presentation.*
import com.uzias.starwarsshop.core.presentation.lifecycles.LifecycleStrategist
import com.uzias.starwarsshop.core.presentation.lifecycles.di.LifecycleStrategistModule
import com.uzias.starwarsshop.products.data.repository.ProductsRepositoryImpl
import com.uzias.starwarsshop.products.data.repository.datasource.networking.ProductsNetworkingDatasourceImpl
import com.uzias.starwarsshop.products.data.repository.datasource.networking.ProductsApi
import com.uzias.starwarsshop.products.data.repository.datasource.networking.ProductsNetworkingDatasource
import com.uzias.starwarsshop.products.domain.repository.ProductsRepository
import com.uzias.starwarsshop.products.domain.usecase.GetProducts
import com.uzias.starwarsshop.products.domain.usecase.GetProductsImpl
import com.uzias.starwarsshop.products.presentation.adapter.ProductsAdapter
import com.uzias.starwarsshop.products.presentation.navigation.ProductsNavigation
import com.uzias.starwarsshop.products.presentation.navigation.ProductsNavigationImpl
import com.uzias.starwarsshop.products.presentation.presenter.ProductsPresenter
import com.uzias.starwarsshop.products.presentation.presenter.ProductsPresenterImpl
import com.uzias.starwarsshop.products.presentation.view.ProductsFragment
import com.uzias.starwarsshop.products.presentation.view.ProductsView
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import kotlinx.android.synthetic.main.fragment_products.*
import kotlinx.android.synthetic.main.state_view_empty.*
import kotlinx.android.synthetic.main.state_view_error.*
import kotlinx.android.synthetic.main.state_view_loading.*
import retrofit2.Retrofit
import javax.inject.Named

@Module(includes = arrayOf(BehavioursModule::class, LifecycleStrategistModule::class))
class ProductsModule {

    @Provides fun providesProductsPresenter(getProducts: GetProducts,
                                            coordinator: BehavioursCoordinator<Any>,
                                            strategist: LifecycleStrategist, productsView: ProductsView,
                                            @IOScheduler scheduler: Scheduler,
                                            @UIScheduler uiScheduler: Scheduler,
                                            navigation: ProductsNavigation)
            : ProductsPresenter = ProductsPresenterImpl(getProducts = getProducts,
            coordinator = coordinator, strategist = strategist, view = productsView,
            ioScheduler = scheduler, navigation = navigation, uiScheduler = uiScheduler)

    @Provides fun providesGetProducts(productsRepository: ProductsRepository)
            : GetProducts = GetProductsImpl(productsRepository = productsRepository)

    @Provides fun providesProductsRepository(productsNetworkingDatasource: ProductsNetworkingDatasource)
            : ProductsRepository = ProductsRepositoryImpl(networking = productsNetworkingDatasource)

    @Provides fun providesNetworkingDatasource(productsApi: ProductsApi)
            : ProductsNetworkingDatasource = ProductsNetworkingDatasourceImpl(productsApi = productsApi)

    @Provides fun providesProductsApi(@Named("retrofitDefault") retrofit: Retrofit)
            : ProductsApi = retrofit.create(ProductsApi::class.java)

    @Provides fun providesNavigation(view: ProductsFragment): ProductsNavigation
            = ProductsNavigationImpl(productsFragment = view)

    @Provides fun providesProductsAdapter(): ProductsAdapter = ProductsAdapter()

    @Provides fun providesEmptyStateView(view: ProductsFragment): EmptyStateView = view

    @Provides fun providesLoadingView(view: ProductsFragment): LoadingView = view

    @Provides fun providesErrorView(view: ProductsFragment): ErrorStateView = view

    @Provides fun providesStrategist(view: ProductsFragment): LifecycleOwner = view

    @Provides fun providesProductsView(view: ProductsFragment): ProductsView = view

    @Provides fun providesToogleRefreshView(view: ProductsFragment): ToogleRefreshView = view

    @Provides fun providesNetworkingView(view: ProductsFragment): NetworkingView = view

    @Provides fun providesPlaceHolder(activity: ProductsFragment) :
            PlaceholderViewsManager = PlaceholderViewsManager(
                loadingViewStub = activity.state_view_loading,
                errorViewStub = activity.state_view_error,
                emptyViewStub = activity.state_view_empty,
                containerView = activity.container)
}