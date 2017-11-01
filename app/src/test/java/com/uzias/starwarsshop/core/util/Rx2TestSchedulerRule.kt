package com.uzias.starwarsshop.core.util

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.ExternalResource

class Rx2TestSchedulerRule : ExternalResource() {

    override public fun before() {
        val scheduler = Schedulers.trampoline()
        RxJavaPlugins.setIoSchedulerHandler({ _ -> scheduler })
        RxJavaPlugins.setInitComputationSchedulerHandler({ _ -> scheduler })
        RxJavaPlugins.setComputationSchedulerHandler({ _ -> scheduler })
        RxJavaPlugins.setNewThreadSchedulerHandler({ _ -> scheduler })
        RxAndroidPlugins.setInitMainThreadSchedulerHandler({ _ -> scheduler })
        RxAndroidPlugins.setMainThreadSchedulerHandler({ _ -> scheduler })
    }

    override fun after() {
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
    }

}
