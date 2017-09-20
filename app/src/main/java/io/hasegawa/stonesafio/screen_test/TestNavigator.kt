package io.hasegawa.stonesafio.screen_test

import com.bluelinelabs.conductor.RouterTransaction
import io.hasegawa.presentation.screen_test.TestContract


class TestNavigator(private val testController: TestController) : TestContract.Navigator {
    override fun goToNextScreen() {
        val router = testController.router
        router.pushController(RouterTransaction.with(TestController(testController.titleId + 1)))
    }
}