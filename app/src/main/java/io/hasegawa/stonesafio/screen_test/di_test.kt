package io.hasegawa.stonesafio.screen_test

import dagger.Component
import dagger.Module
import dagger.Provides
import io.hasegawa.data.test.InMemTestRepository
import io.hasegawa.presentation.screen_test.TestLoaderUC
import io.hasegawa.presentation.screen_test.TestPresenter
import io.hasegawa.stonesafio.di.BaseDIComponent
import io.hasegawa.stonesafio.domain.test.TestLoadUC
import javax.inject.Scope


@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class TestControllerScope

@Module
class TestDIModule(private val controller: TestController) {
    @Provides
    @TestControllerScope
    fun providePresenter(): TestPresenter {
        return TestPresenter(
                TestNavigator(controller),
                TestLoaderUC(TestLoadUC(InMemTestRepository())))
    }
}

@TestControllerScope
@Component(dependencies = arrayOf(BaseDIComponent::class),
        modules = arrayOf(TestDIModule::class))
interface TestDIComponent {
    companion object {
        fun getPresenter(controller: TestController) =
                DaggerTestDIComponent.builder()
                        .baseDIComponent(BaseDIComponent.instance)
                        .testDIModule(TestDIModule(controller))
                        .build()
                        .getPresenter()
    }

    fun getPresenter(): TestPresenter
}
