package com.uzias.starwarsshop.core.behaviours

import com.uzias.starwarsshop.core.behaviours.emptystate.AssignEmptyCoordination
import com.uzias.starwarsshop.core.behaviours.errornetworkingstate.NetworkingErrorCoordination
import com.uzias.starwarsshop.core.behaviours.errorstate.AssignErrorCoordination
import com.uzias.starwarsshop.core.behaviours.loadingstate.LoadingCoordination
import com.uzias.starwarsshop.core.behaviours.refreshtooglestate.RefreshToogleCoordination
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import org.reactivestreams.Publisher

class BehavioursCoordinator<T>(private val dealWithEmptyState: AssignEmptyCoordination<T>,
                               private val loadingCoordination: LoadingCoordination<T>,
                               private val errorCoordination: AssignErrorCoordination<T>,
                               private val networkingCoordination: NetworkingErrorCoordination<T>,
                               private val toogleCoordination: RefreshToogleCoordination<T>):
        FlowableTransformer<T, T> {

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream
                .compose(dealWithEmptyState)
                .compose(loadingCoordination)
                .compose(errorCoordination)
                .compose(networkingCoordination)
                .compose(toogleCoordination)
    }

}
