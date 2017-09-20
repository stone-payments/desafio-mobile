package io.hasegawa.presentation.screen_test

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import io.hasegawa.presentation.screen_test.TestContract.StateEvent.TextError
import io.hasegawa.presentation.screen_test.TestContract.StateEvent.TextLoaded
import io.hasegawa.presentation.screen_test.TestContract.ViewState.LoadingData
import io.hasegawa.presentation.screen_test.TestContract.ViewState.Okay
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.BeforeClass
import org.junit.Test
import java.util.concurrent.TimeUnit

class TestPresenterTest {
    companion object {
        @BeforeClass
        @JvmStatic
        fun init() {
            // Tell RxAndroid to not use android main ui thread scheduler
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        }
    }

    @Test
    fun testLoadingThenOkay() {
        val loaderUC: TestLoaderUC = mock()
        val ts = TestScheduler()

        whenever(loaderUC.buildObservable())
                .thenReturn(
                        Observable
                                .just(TextLoaded("Loaded!") as TestContract.StateEvent)
                                .delay(100, TimeUnit.MILLISECONDS, ts)
                )

        val robot = TestViewRobot(TestPresenter(mock(), loaderUC))
        ts.advanceTimeBy(100, TimeUnit.MILLISECONDS)

        val events = robot.renderEvents.take(2).test()
        events.awaitTerminalEvent(2, TimeUnit.SECONDS)
        events.assertValues(LoadingData, Okay("Loaded!"))
    }

    @Test
    fun testLoadingThenError() {
        val loaderUC: TestLoaderUC = mock()
        val ts = TestScheduler()

        whenever(loaderUC.buildObservable())
                .thenReturn(
                        Observable
                                .just(TextError("Ops!") as TestContract.StateEvent)
                                .delay(100, TimeUnit.MILLISECONDS, ts)
                )

        val robot = TestViewRobot(TestPresenter(mock(), loaderUC))
        ts.advanceTimeBy(200, TimeUnit.MILLISECONDS)

        val events = robot.renderEvents.take(2).test()
        events.awaitTerminalEvent(2, TimeUnit.SECONDS)
        events.assertValues(LoadingData, TestContract.ViewState.Error("Ops!"))
    }

    @Test
    fun testLoadingOkayError() {
        val loaderUC: TestLoaderUC = mock()
        val ts = TestScheduler()

        whenever(loaderUC.buildObservable())
                .thenReturn(
                        Observable
                                .fromIterable(
                                        listOf(TextLoaded("Loaded!") as TestContract.StateEvent,
                                                TextError("Ops!")))
                                .delay(100, TimeUnit.MILLISECONDS, ts)
                )

        val robot = TestViewRobot(TestPresenter(mock(), loaderUC))
        ts.advanceTimeBy(200, TimeUnit.MILLISECONDS)

        val events = robot.renderEvents.take(3).test()
        events.awaitTerminalEvent(2, TimeUnit.SECONDS)
        events.assertValues(LoadingData, Okay("Loaded!"), TestContract.ViewState.Error("Ops!"))
    }

    @Test
    fun testReloadData() {
        val loaderUC: TestLoaderUC = mock()
        whenever(loaderUC.buildObservable())
                .thenReturn(Observable.empty())
                .thenReturn(
                        Observable.just(TextLoaded("Loaded 2!") as TestContract.StateEvent))

        val robot = TestViewRobot(TestPresenter(mock(), loaderUC))
        robot.fireReloadDataIntent()

        val events = robot.renderEvents.take(2).test()
        events.awaitTerminalEvent(2, TimeUnit.SECONDS)
        events.assertValues(LoadingData, Okay("Loaded 2!"))
    }

    @Test
    fun testNextScreenNavigation() {
        val navigator: TestContract.Navigator = mock()
        val loaderUC: TestLoaderUC = mock()
        whenever(loaderUC.buildObservable()).thenReturn(Observable.empty())

        val robot = TestViewRobot(TestPresenter(navigator, loaderUC))
        robot.fireNextScreenIntent()

        verify(navigator).goToNextScreen()
        verifyNoMoreInteractions(navigator)
    }
}