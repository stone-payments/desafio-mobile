package com.uzias.starwarsshop.core.behaviours.coordination

import com.uzias.starwarsshop.core.util.MockitoHelpers.oneTimeOnly
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.uzias.starwarsshop.core.behaviours.errornetworkingstate.NetworkingErrorCoordination
import com.uzias.starwarsshop.core.errors.ContentNotFoundError
import com.uzias.starwarsshop.core.networking.errors.NetworkingError
import com.uzias.starwarsshop.core.presentation.NetworkingView
import io.reactivex.Flowable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class NetworkingErrorCoordinationTest {
    private val uiScheduler = Schedulers.trampoline()
    private lateinit var networkingErrorCoordination: NetworkingErrorCoordination<Int>
    private val networkingError: Action = mock()

    @Before
    fun beforeEachTest() {
        val view = object : NetworkingView {
            override fun networkingErrorState(): Action  = networkingError
        }
        networkingErrorCoordination = NetworkingErrorCoordination(view, uiScheduler)
    }

    @Test
    fun should_not_show_error_when_emit_item() {
        Flowable.just(10, 20, 30)
                .compose(networkingErrorCoordination)
                .subscribe()

        verify(networkingError, never()).run()
    }

    @Test
    fun should_not_show_error_when_emit_empty() {
        Flowable.empty<Int>()
                .compose(networkingErrorCoordination)
                .subscribe()

        verify(networkingError, never()).run()
    }

    @Test
    fun should_not_show_error_when_emit_error_not_found() {
        Flowable.error<Int>(ContentNotFoundError())
                .compose(networkingErrorCoordination)
                .subscribe({}, {}, {})

        verify(networkingError, never()).run()
    }

    @Test
    fun should_show_error_when_emit_networking_error() {
        Flowable.error<Int>(NetworkingError("Error Networking"))
                .compose(networkingErrorCoordination)
                .subscribe({}, {}, {})

        verify(networkingError, oneTimeOnly()).run()
    }
}
