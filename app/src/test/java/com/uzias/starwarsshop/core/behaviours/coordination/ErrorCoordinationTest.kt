package com.uzias.starwarsshop.core.behaviours.coordination

import com.uzias.starwarsshop.core.util.MockitoHelpers.oneTimeOnly
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.uzias.starwarsshop.core.behaviours.errorstate.AssignErrorCoordination
import com.uzias.starwarsshop.core.presentation.ErrorStateView
import io.reactivex.Flowable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class ErrorCoordinationTest {
    private val uiScheduler = Schedulers.trampoline()
    private lateinit var errorCoordination: AssignErrorCoordination<Int>
    private val showError: Action = mock()
    private val hideError: Action = mock()

    @Before
    fun beforeEachTest() {
        val view = object : ErrorStateView {
            override fun showErrorState(): Action = showError
            override fun hideErrorState(): Action = hideError
        }
        errorCoordination = AssignErrorCoordination(view, uiScheduler)
    }

    @Test
    fun should_hide_and_never_show_error_when_emit_item() {
        Flowable.just(10, 20, 30)
                .compose(errorCoordination)
                .subscribe()

        verify(hideError, oneTimeOnly()).run()
        verify(showError, never()).run()
    }

    @Test
    fun should_hide_and_never_show_error_when_emit_empty() {
        Flowable.empty<Int>()
                .compose(errorCoordination)
                .subscribe()

        verify(hideError, oneTimeOnly()).run()
        verify(showError, never()).run()
    }

    @Test
    fun should_hide_and_show_when_emit_error() {
        Flowable.error<Int>(RuntimeException("SOME ERROR!"))
                .compose(errorCoordination)
                .subscribe(
                        {},
                        {},
                        {}
                )

        verify(showError, oneTimeOnly()).run()
        verify(hideError, oneTimeOnly()).run()
    }
}
