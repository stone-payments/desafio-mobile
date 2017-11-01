package com.uzias.starwarsshop.core.behaviours.coordination

import com.uzias.starwarsshop.core.util.MockitoHelpers.oneTimeOnly
import com.nhaarman.mockito_kotlin.inOrder
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.uzias.starwarsshop.core.behaviours.refreshtooglestate.RefreshToogleCoordination
import com.uzias.starwarsshop.core.errors.ContentNotFoundError
import com.uzias.starwarsshop.core.presentation.ToogleRefreshView
import io.reactivex.Flowable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class RefreshToogleCoordinationTest {
    private val uiScheduler = Schedulers.trampoline()
    private lateinit var refreshToogleCoordination: RefreshToogleCoordination<Int>
    private val disableRefresh: Action = mock()
    private val enableRefresh: Action = mock()

    @Before
    fun beforeEachTest() {
        val view = object : ToogleRefreshView {
            override fun disableRefresh(): Action = disableRefresh

            override fun enableRefresh(): Action = enableRefresh
        }
        refreshToogleCoordination = RefreshToogleCoordination(view, uiScheduler)
    }

    @Test
    fun should_disable_first_and_enable_after_emit_item() {
        Flowable.just(10, 20, 30, 40, 50)
                .compose<Int>(refreshToogleCoordination)
                .subscribe()

        checkForDisableFirstEnableAfter()
    }

    @Test
    fun should_disable_first_and_enable_after_emit_empty() {
        Flowable.empty<Int>()
                .compose<Int>(refreshToogleCoordination)
                .subscribe()

        checkForDisableFirstEnableAfter()
    }

    @Test
    fun should_not_enable_after_emit_not_special_error() {
        Flowable.error<Int>(RuntimeException("SOME ERRROR NOT SPECIAL"))
                .compose<Int>(refreshToogleCoordination)
                .subscribe({}, {}, {})

        checkForDisableFirstNotEnableAfter()
    }

    @Test
    fun should_enable_after_emit_special_error() {
        Flowable.error<Int>(ContentNotFoundError())
                .compose<Int>(refreshToogleCoordination)
                .subscribe({}, {}, {})

        checkForDisableFirstEnableAfter()
    }

    private fun checkForDisableFirstEnableAfter() {
        val inOrder = inOrder(disableRefresh, enableRefresh)
        inOrder.verify(disableRefresh, oneTimeOnly()).run()
        inOrder.verify(enableRefresh, oneTimeOnly()).run()
    }

    private fun checkForDisableFirstNotEnableAfter() {
        val inOrder = inOrder(disableRefresh, enableRefresh)
        inOrder.verify(disableRefresh, oneTimeOnly()).run()
        inOrder.verify(enableRefresh, never()).run()
    }
}