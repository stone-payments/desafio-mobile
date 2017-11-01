package com.uzias.starwarsshop.transactions.presentation.presenter

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
import com.uzias.starwarsshop.transactions.domain.model.Transaction
import com.uzias.starwarsshop.transactions.domain.usecase.GetTransactions
import com.uzias.starwarsshop.transactions.presentation.view.TransactionsView
import com.uzias.starwarsshop.util.BehavioursRobot
import com.uzias.starwarsshop.util.FakeBaseView
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TransactionsPresenterImplTest {

    private val view: TransactionsView = mock()
    private val fakeBaseView = FakeBaseView()
    private val dispose: DisposeStrategy = DisposeStrategy()
    private lateinit var strategist: LifecycleStrategist
    private val schedule = Schedulers.trampoline()
    private val getTransactions: GetTransactions = mock()
    private val coordinator = BehavioursCoordinator<Any>(
            AssignEmptyCoordination(fakeBaseView, schedule),
            LoadingCoordination(fakeBaseView, schedule),
            AssignErrorCoordination(fakeBaseView, schedule),
            NetworkingErrorCoordination(fakeBaseView, schedule),
            RefreshToogleCoordination(fakeBaseView, schedule))

    private lateinit var presenter: TransactionsPresenter

    @Before
    fun setUp() {
        strategist = LifecycleStrategist(owner = mockLifecycleOwner(), strategy = dispose)

        presenter = TransactionsPresenterImpl(
                getTransactions = getTransactions,
                coordinator = coordinator,
                strategist = strategist,
                view = view,
                ioScheduler = schedule,
                uiScheduler = schedule)

        given(getTransactions.run()).willReturn(Flowable.just(listOf(Transaction())))
        presenter.getTransactions()
    }

    @Test
    fun must_call_when_get_transactions() {
        BehavioursRobot.with(fakeBaseView)
                .showLoadingFirstHideLoadingAfter()
                .disableRefreshFirstAndEnableAfter()
                .shouldNotShowEmptyState()
                .shouldNotShowErrorState()
                .shouldNotReportNetworkingError()

        verify(view).setList(any())
    }

    private fun mockLifecycleOwner(): LifecycleOwner {
        val lifecycleOwner: LifecycleOwner = mock()

        val registry = LifecycleRegistry(lifecycleOwner)
        registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        given(lifecycleOwner.lifecycle).willReturn(registry)
        return lifecycleOwner
    }

}