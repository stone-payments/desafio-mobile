package com.uzias.starwarsshop.core.behaviours

import com.uzias.starwarsshop.core.util.MockitoHelpers.oneTimeOnly
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import com.uzias.starwarsshop.util.ErrorPredicate
import io.reactivex.Flowable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class HideAtStartShowAtErrorTest {
    var scheduler = Schedulers.trampoline()
    var positive = object : ErrorPredicate {
        override fun evaluate(error: Throwable): Boolean = true
    }
    var negative = object : ErrorPredicate {
        override fun evaluate(error: Throwable): Boolean = false
    }

    var whenStart: Action = mock()
    var atError: Action = mock()

    @Test
    fun should_hide_at_start_and_not_show_error_when_emit_item() {
        Flowable.just("A", "B", "C")
                .compose(HideAtStartShowAtError(whenStart, atError, positive, scheduler))
                .subscribe()

        verify(whenStart, oneTimeOnly()).run()
        verifyZeroInteractions(atError)
    }

    @Test
    fun should_hide_at_start_and_not_show_error_when_emit_empty() {
        Flowable.empty<Any>()
                .compose(HideAtStartShowAtError(whenStart, atError, positive, scheduler))
                .subscribe()

        verify(whenStart, oneTimeOnly()).run()
        verifyZeroInteractions(atError)
    }

    @Test
    fun should_hide_at_start_and_not_show_error_when_evaluation_fail() {
        Flowable.error<Any>(RuntimeException("SOME ERROR"))
                .compose(HideAtStartShowAtError(whenStart, atError, negative, scheduler))
                .subscribe({}, {}, {})

        verify(whenStart, oneTimeOnly()).run()
        verifyZeroInteractions(atError)
    }

    @Test
    fun should_hide_at_start_and_not_show_error_when_evaluation_error_sucessfull() {
        Flowable.error<Any>(RuntimeException("SOME ERROR"))
                .compose(HideAtStartShowAtError(whenStart, atError, positive, scheduler))
                .subscribe({}, {}, {})

        verify(whenStart, oneTimeOnly()).run()
        verify(atError, oneTimeOnly()).run()
    }
}
