package com.uzias.starwarsshop.checkout.presentation.presenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import com.nhaarman.mockito_kotlin.*
import com.uzias.starwarsshop.checkout.presentation.model.PresentationCreditCard
import com.uzias.starwarsshop.checkout.presentation.navigation.CheckoutNavigation
import com.uzias.starwarsshop.checkout.presentation.view.CheckoutView
import com.uzias.starwarsshop.core.presentation.lifecycles.LifecycleStrategist
import com.uzias.starwarsshop.transactions.domain.usecase.RegisterTransaction
import io.reactivex.schedulers.Schedulers
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import com.uzias.starwarsshop.core.behaviours.loadingstate.LoadingCoordination
import com.uzias.starwarsshop.core.behaviours.BehavioursCoordinator
import com.uzias.starwarsshop.core.behaviours.emptystate.AssignEmptyCoordination
import com.uzias.starwarsshop.core.behaviours.errornetworkingstate.NetworkingErrorCoordination
import com.uzias.starwarsshop.core.behaviours.errorstate.AssignErrorCoordination
import com.uzias.starwarsshop.core.behaviours.refreshtooglestate.RefreshToogleCoordination
import com.uzias.starwarsshop.core.presentation.lifecycles.DisposeStrategy
import com.uzias.starwarsshop.transactions.domain.model.Transaction
import com.uzias.starwarsshop.util.FakeBaseView
import io.reactivex.Flowable
import org.junit.Before
import android.arch.lifecycle.LifecycleRegistry
import com.uzias.starwarsshop.util.BehavioursRobot


@RunWith(MockitoJUnitRunner::class)
class CheckoutPresenterImplTest {

    private val navigation: CheckoutNavigation = mock()
    private val view: CheckoutView = mock()
    private val fakeBaseView = FakeBaseView()
    private val dispose: DisposeStrategy = DisposeStrategy()
    private lateinit var strategist: LifecycleStrategist
    private val schedule = Schedulers.trampoline()
    private val registerTransaction: RegisterTransaction = mock()
    private val coordinator = BehavioursCoordinator<Any>(
            AssignEmptyCoordination(fakeBaseView, schedule),
            LoadingCoordination(fakeBaseView, schedule),
            AssignErrorCoordination(fakeBaseView, schedule),
            NetworkingErrorCoordination(fakeBaseView, schedule),
            RefreshToogleCoordination(fakeBaseView, schedule))

    private val totalPrice = 7900.0

    private lateinit var presenter: CheckoutPresenter

    @Before
    fun setUp(){
        strategist = LifecycleStrategist(owner = mockLifecycleOwner(), strategy = dispose)
        presenter = CheckoutPresenterImpl(navigation = navigation, view = view,
                totalPrice = totalPrice, registerTransaction = registerTransaction, coordinator = coordinator,
                ioScheduler = schedule, uiScheduler = schedule, strategist = strategist)
    }

    @Test
    fun must_call_when_click_button_checkout_with_credit_card(){
        given(registerTransaction.setField(any(), any())).willReturn(registerTransaction)
        given(registerTransaction.run()).willReturn(Flowable.just(Transaction()))

        presenter.resultCreditCard(PresentationCreditCard(cvv = "333"))
        presenter.clickedButtonCheckout()

        BehavioursRobot.with(fakeBaseView)
                .showLoadingFirstHideLoadingAfter()
                .disableRefreshFirstAndEnableAfter()
                .shouldNotShowEmptyState()
                .shouldNotShowErrorState()
                .shouldNotReportNetworkingError()

        verify(view).finishActivityWithResultOk()
    }

    @Test
    fun must_call_when_click_button_checkout_without_credit_card(){
        presenter.clickedButtonCheckout()
        verify(navigation).goToAddCreditCard()
    }

    @Test
    fun must_call_when_result_card_set(){
        val creditCard = PresentationCreditCard(cvv = "333")
        presenter.resultCreditCard(creditCard)
        verify(view).setCreditCard(creditCard)
        verify(view).changeTextButtonCheckoutToConfirm()
    }

    private fun mockLifecycleOwner(): LifecycleOwner {
        val lifecycleOwner:LifecycleOwner = mock()

        val registry = LifecycleRegistry(lifecycleOwner)
        registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        given(lifecycleOwner.lifecycle).willReturn(registry)
        return lifecycleOwner
    }

}