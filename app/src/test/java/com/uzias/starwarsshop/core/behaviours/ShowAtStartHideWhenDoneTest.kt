package com.uzias.starwarsshop.core.behaviours

import com.uzias.starwarsshop.core.util.MockitoHelpers.oneTimeOnly
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Flowable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class ShowAtStartHideWhenDoneTest {
    var targetScheduler = Schedulers.trampoline()
    var whenStart: Action = mock()
    var whenDone: Action = mock()

    @Test
    fun should_hide_at_start_and_not_show_error_when_emit_item() {
        Flowable.just("AB", "CD")
                .compose(ShowAtStartHideWhenDone(whenStart, whenDone, targetScheduler))
                .subscribe()

        verifyShowAndHideWithFlow()
    }

    @Test
    fun should_hide_at_start_and_not_show_error_when_emit_empty() {
        Flowable.empty<Any>()
                .compose(ShowAtStartHideWhenDone(whenStart, whenDone, targetScheduler))
                .subscribe()

        verifyShowAndHideWithFlow()
    }

    @Test
    fun should_hide_at_start_and_not_show_error_when_emit_error() {
        Flowable.error<Any>(RuntimeException("Damn it!!!"))
                .compose(ShowAtStartHideWhenDone(whenStart, whenDone, targetScheduler))
                .subscribe({}, {}, {})

        verifyShowAndHideWithFlow()
    }

    private fun verifyShowAndHideWithFlow() {
        verify(whenStart, oneTimeOnly()).run()
        verify(whenDone, oneTimeOnly()).run()
    }
}
