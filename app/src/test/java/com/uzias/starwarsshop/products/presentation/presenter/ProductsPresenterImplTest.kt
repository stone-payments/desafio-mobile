package com.uzias.starwarsshop.products.presentation.presenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import com.nhaarman.mockito_kotlin.*
import com.uzias.starwarsshop.core.behaviours.BehavioursCoordinator
import com.uzias.starwarsshop.core.behaviours.emptystate.AssignEmptyCoordination
import com.uzias.starwarsshop.core.behaviours.errornetworkingstate.NetworkingErrorCoordination
import com.uzias.starwarsshop.core.behaviours.errorstate.AssignErrorCoordination
import com.uzias.starwarsshop.core.behaviours.loadingstate.LoadingCoordination
import com.uzias.starwarsshop.core.behaviours.refreshtooglestate.RefreshToogleCoordination
import com.uzias.starwarsshop.core.presentation.lifecycles.DisposeStrategy
import com.uzias.starwarsshop.core.presentation.lifecycles.LifecycleStrategist
import com.uzias.starwarsshop.products.domain.model.Product
import com.uzias.starwarsshop.products.domain.usecase.GetProducts
import com.uzias.starwarsshop.products.presentation.navigation.ProductsNavigation
import com.uzias.starwarsshop.products.presentation.view.ProductsView
import com.uzias.starwarsshop.util.BehavioursRobot
import com.uzias.starwarsshop.util.FakeBaseView
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProductsPresenterImplTest {

    private val navigation: ProductsNavigation = mock()
    private val view: ProductsView = mock()
    private val fakeBaseView = FakeBaseView()
    private val dispose: DisposeStrategy = DisposeStrategy()
    private lateinit var strategist: LifecycleStrategist
    private val schedule = Schedulers.trampoline()
    private val getProducts: GetProducts = mock()
    private val coordinator = BehavioursCoordinator<Any>(
            AssignEmptyCoordination(fakeBaseView, schedule),
            LoadingCoordination(fakeBaseView, schedule),
            AssignErrorCoordination(fakeBaseView, schedule),
            NetworkingErrorCoordination(fakeBaseView, schedule),
            RefreshToogleCoordination(fakeBaseView, schedule))

    private lateinit var presenter: ProductsPresenter

    @Before
    fun setUp() {
        strategist = LifecycleStrategist(owner = mockLifecycleOwner(), strategy = dispose)

        presenter = ProductsPresenterImpl(
                getProducts = getProducts,
                coordinator = coordinator,
                strategist = strategist,
                view = view,
                navigation = navigation,
                ioScheduler = schedule,
                uiScheduler = schedule)

        given(getProducts.run()).willReturn(Flowable.just(listOf(Product(price = 99.00))))
        presenter.getProducts()
    }

    @Test
    fun must_call_when_get_products() {
        BehavioursRobot.with(fakeBaseView)
                .showLoadingFirstHideLoadingAfter()
                .disableRefreshFirstAndEnableAfter()
                .shouldNotShowEmptyState()
                .shouldNotShowErrorState()
                .shouldNotReportNetworkingError()

        verify(view).setList(any())
    }

    @Test
    fun must_call_when_click_button_add() {
        presenter.clickedButtonAdd(0)
        verify(view, times(2)).setList(any())
        verify(view).showButtonBottom()
        verify(view).setTextButtonTotal(99.00)
    }

    @Test
    fun must_call_when_click_button_remove() {
        presenter.clickedButtonRemove(0)
        verify(view, times(2)).setList(any())
        verify(view).showButtonBottom()
        verify(view).setTextButtonTotal(-99.0)
    }

    @Test
    fun must_call_hide_button_when_remove_all_item_added() {
        presenter.clickedButtonAdd(0)
        presenter.clickedButtonRemove(0)
        verify(view).hideButtonBottom()
    }

    private fun mockLifecycleOwner(): LifecycleOwner {
        val lifecycleOwner: LifecycleOwner = mock()

        val registry = LifecycleRegistry(lifecycleOwner)
        registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        given(lifecycleOwner.lifecycle).willReturn(registry)
        return lifecycleOwner
    }
}