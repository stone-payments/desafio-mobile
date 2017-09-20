package io.hasegawa.stonesafio.di

import dagger.Component
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseScope

@BaseScope
@Component(dependencies = arrayOf(AppDIComponent::class))
interface BaseDIComponent : AppDIComponent {
    companion object {
        lateinit var instance: BaseDIComponent
            private set

        fun initialize(appDIComponent: AppDIComponent) {
            try {
                instance
            } catch (e: UninitializedPropertyAccessException) {
                DaggerBaseDIComponent.builder()
                        .appDIComponent(appDIComponent)
                        .build()
                        .also { instance = it }
            }
        }
    }
}
