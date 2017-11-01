package com.uzias.starwarsshop.core.behaviours.coordination

import com.uzias.starwarsshop.core.util.MockitoHelpers.oneTimeOnly
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.uzias.starwarsshop.core.behaviours.emptystate.AssignEmptyCoordination
import com.uzias.starwarsshop.core.errors.ContentNotFoundError
import com.uzias.starwarsshop.core.presentation.EmptyStateView
import io.reactivex.Flowable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class EmptyCoordinationTest {
    private val uiScheduler = Schedulers.trampoline()
    private lateinit var emptyCoordination: AssignEmptyCoordination<Int>
    private val showEmtpy: Action = mock()
    private val hideEmtpy: Action = mock()

    @Before
    fun beforeEachTest() {
        val view = object : EmptyStateView {
            override fun showEmptyState(): Action = showEmtpy
            override fun hideEmptyState(): Action = hideEmtpy
        }
        emptyCoordination = AssignEmptyCoordination(view, uiScheduler)
    }

    @Test
    fun should_hide_and_never_show_empty_when_emit_item() {
        Flowable.just(10, 20, 30)
                .compose(emptyCoordination)
                .subscribe()

        verify(hideEmtpy, oneTimeOnly()).run()
        verify(showEmtpy, never()).run()
    }

    @Test
    fun should_hide_and_never_show_empty_when_emit_empty() {
        Flowable.empty<Int>()
                .compose(emptyCoordination)
                .subscribe()

        verify(hideEmtpy, oneTimeOnly()).run()
        verify(showEmtpy, never()).run()
    }

    @Test
    fun should_hide_and_never_show_empty_when_emit_not_target_error() {
        Flowable.error<Int>(RuntimeException("Error not target"))
                .compose(emptyCoordination)
                .subscribe(
                        {},
                        {},
                        {}
                )

        verify(hideEmtpy, oneTimeOnly()).run()
        verify(showEmtpy, never()).run()
    }

    @Test
    fun should_hide_and_show_empty_when_emit_content_not_found_error() {
        Flowable.error<Int>(ContentNotFoundError())
                .compose(emptyCoordination)
                .subscribe({}, {}, {})

        verify(hideEmtpy, oneTimeOnly()).run()
        verify(showEmtpy, oneTimeOnly()).run()
    }
}