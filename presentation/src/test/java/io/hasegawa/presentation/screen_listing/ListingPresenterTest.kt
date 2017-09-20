package io.hasegawa.presentation.screen_listing

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.hasegawa.presentation.screen_listing.ListingContract.ListingErrorType.ConnectionIssue
import io.hasegawa.presentation.screen_listing.ListingContract.StateEvent
import io.hasegawa.presentation.screen_listing.ListingContract.ViewState
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.PublishSubject
import org.junit.BeforeClass
import org.junit.Test
import java.util.concurrent.TimeUnit


class ListingPresenterTest {
    companion object {
        @BeforeClass
        @JvmStatic
        fun init() {
            // Tell RxAndroid to not use android main ui thread scheduler
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        }
    }

    @Test
    fun testNumInCart() {
        val fetcherUC: ListingFetcherUC = mock()
        val ts = TestScheduler()
        fetcherUC.setFetcherProducts(Mother.products, ts)
        val numInCartEvents = PublishSubject.create<StateEvent>()

        val robot = ListingViewRobot(ListingPresenter(mock(), fetcherUC,
                defaultAddToCartUC(numInCartEvents)))
        numInCartEvents.onNext(StateEvent.SetNumberInCart(42))
        ts.advanceTimeBy(200, TimeUnit.MILLISECONDS)


        val events = robot.renderEvents.takeUntil { it.numberInCart == 42 }.test()
        events.awaitTerminalEvent(2, TimeUnit.SECONDS)
        assert.that(events.values().lastOrNull()?.numberInCart, equalTo(42))
    }

    @Test
    fun testOkayFetch() {
        val fetcherUC: ListingFetcherUC = mock()
        val ts = TestScheduler()
        fetcherUC.setFetcherProducts(Mother.products, ts)

        val robot = ListingViewRobot(ListingPresenter(mock(), fetcherUC, defaultAddToCartUC()))
        ts.advanceTimeBy(200, TimeUnit.MILLISECONDS)

        val events = robot.renderEvents.take(2).test()
        events.awaitTerminalEvent(2, TimeUnit.SECONDS)
        events.assertValues(ViewState.Loading(0, ""), ViewState.Okay(Mother.products, 0, ""))
    }

    @Test
    fun testNoInternet() {
        val fetcherUC: ListingFetcherUC = mock()
        val ts = TestScheduler()

        whenever(fetcherUC.buildObservable(any()))
                .thenReturn(Observable.just(ListingFetcherUC.Result.NoInternet)
                        .delay(100, TimeUnit.MILLISECONDS, ts)
                        .cast(ListingFetcherUC.Result::class.java))

        val robot = ListingViewRobot(ListingPresenter(mock(), fetcherUC, defaultAddToCartUC()))
        ts.advanceTimeBy(200, TimeUnit.MILLISECONDS)

        val events = robot.renderEvents.take(2).test()
        events.awaitTerminalEvent(2, TimeUnit.SECONDS)
        events.assertValues(ViewState.Loading(0, ""), ViewState.Error(ConnectionIssue, 0, ""))
    }

    @Test
    fun testNavigationCart() {
        val navigator: ListingContract.Navigator = mock()
        val fetcherUC: ListingFetcherUC = mock()
        fetcherUC.setFetcherProducts(emptyList(), TestScheduler())

        val robot = ListingViewRobot(ListingPresenter(navigator, fetcherUC, defaultAddToCartUC()))
        robot.fireGoToCartIntent()

        verify(navigator).goToCart()
    }

    @Test
    fun testFilter() {
        val fetcherUC: ListingFetcherUC = mock()
        val ts = TestScheduler()
        val getFilterObs = fetcherUC.setFetcherProducts(Mother.products, ts)

        val robot = ListingViewRobot(ListingPresenter(mock(), fetcherUC, defaultAddToCartUC()))
        val filter = getFilterObs().take(1).test()
        ts.advanceTimeBy(200, TimeUnit.MILLISECONDS)

        robot.fireFilterChangedIntent("l")
        robot.fireFilterChangedIntent("lu")
        robot.fireFilterChangedIntent("luz")

        filter.awaitTerminalEvent(2, TimeUnit.SECONDS)
        filter.assertValues("luz")
        // The ListingFetcherUC is responsible for the filtered list of products,
        // therefore, we don't test it here!
    }

    private fun ListingFetcherUC.setFetcherProducts(
            list: List<ListingContract.Product>, scheduler: Scheduler): () -> Observable<String> {
        val filterCaptor = argumentCaptor<ListingFetcherUC.Params>()
        whenever(this.buildObservable(filterCaptor.capture()))
                .thenReturn(Observable.just(ListingFetcherUC.Result.Success(list))
                        .delay(100, TimeUnit.MILLISECONDS, scheduler)
                        .cast(ListingFetcherUC.Result::class.java))
        return { filterCaptor.firstValue.filterByTitleObs }
    }

    private fun defaultAddToCartUC(eventSubj: PublishSubject<StateEvent>? = null): ListingAddToCartUC {
        val uc: ListingAddToCartUC = mock()
        whenever(uc.buildObservable(any())).thenReturn(eventSubj ?: Observable.empty())
        return uc
    }
}

private object Mother {
    val products = listOf(
            ListingContract.Product(
                    id = "0",
                    title = "Sabre de luz",
                    price = 150000,
                    seller = "Mario Mota",
                    thumbnailUrl = "http://www.obrigadopelospeixes.com/wp-content/uploads/2015/12/kalippe_lightsaber_by_jnetrocks-d4dyzpo1-1024x600.jpg",
                    inCart = false),
            ListingContract.Product(
                    id = "1",
                    title = "Capacete de StormTrooper",
                    price = 30000,
                    seller = "Edu Guimar\u00e3es",
                    thumbnailUrl = "https://cmkt-image-prd.global.ssl.fastly.net/0.1.0/ps/760104/300/200/m1/fpnw/wm0/clean_tt_v001.0001-.png?1446786887&s=dcba72825ebb6982f69cd9aeeddcf9ca",
                    inCart = false)
    )
}
