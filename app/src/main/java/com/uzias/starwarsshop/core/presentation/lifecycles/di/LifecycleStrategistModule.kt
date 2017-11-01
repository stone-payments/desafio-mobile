package com.uzias.starwarsshop.core.presentation.lifecycles.di

import android.arch.lifecycle.LifecycleOwner
import com.uzias.starwarsshop.core.presentation.lifecycles.DisposeStrategy
import com.uzias.starwarsshop.core.presentation.lifecycles.LifecycleStrategist
import dagger.Module
import dagger.Provides

@Module
class LifecycleStrategistModule {

    @Provides
    fun strategist(owner: LifecycleOwner): LifecycleStrategist {
        return LifecycleStrategist(owner, DisposeStrategy())
    }

}
