package com.uzias.starwarsshop.transactions.di

import android.arch.lifecycle.LifecycleOwner
import com.uzias.starwarsshop.core.application.di.IOScheduler
import com.uzias.starwarsshop.core.application.di.UIScheduler
import com.uzias.starwarsshop.core.behaviours.BehavioursCoordinator
import com.uzias.starwarsshop.core.behaviours.di.BehavioursModule
import com.uzias.starwarsshop.core.presentation.*
import com.uzias.starwarsshop.core.presentation.lifecycles.LifecycleStrategist
import com.uzias.starwarsshop.core.presentation.lifecycles.di.LifecycleStrategistModule
import com.uzias.starwarsshop.transactions.data.TransactionsRepositoryImpl
import com.uzias.starwarsshop.transactions.data.datasource.database.TransactionsDatabase
import com.uzias.starwarsshop.transactions.data.datasource.database.TransactionsDatabaseImpl
import com.uzias.starwarsshop.transactions.data.datasource.networking.TransactionsApi
import com.uzias.starwarsshop.transactions.data.datasource.networking.TransactionsNetworkingDatasource
import com.uzias.starwarsshop.transactions.data.datasource.networking.TransactionsNetworkingDatasourceImpl
import com.uzias.starwarsshop.transactions.domain.repository.TransactionsRepository
import com.uzias.starwarsshop.transactions.domain.usecase.GetTransactions
import com.uzias.starwarsshop.transactions.domain.usecase.GetTransactionsImpl
import com.uzias.starwarsshop.transactions.domain.usecase.RegisterTransaction
import com.uzias.starwarsshop.transactions.domain.usecase.RegisterTransactionImpl
import com.uzias.starwarsshop.transactions.presentation.adapter.TransactionsAdapter
import com.uzias.starwarsshop.transactions.presentation.presenter.TransactionsPresenter
import com.uzias.starwarsshop.transactions.presentation.presenter.TransactionsPresenterImpl
import com.uzias.starwarsshop.transactions.presentation.view.TransactionsFragment
import com.uzias.starwarsshop.transactions.presentation.view.TransactionsView
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import kotlinx.android.synthetic.main.fragment_transactions.*
import kotlinx.android.synthetic.main.state_view_empty.*
import kotlinx.android.synthetic.main.state_view_error.*
import kotlinx.android.synthetic.main.state_view_loading.*
import retrofit2.Retrofit
import javax.inject.Named

@Module(includes = arrayOf(BehavioursModule::class, LifecycleStrategistModule::class, TransactionsUseCaseModule::class))
class TransactionsModule {

    @Provides fun providesTransactionsPresenter(getTransactions: GetTransactions,
                                                coordinator: BehavioursCoordinator<Any>,
                                         strategist: LifecycleStrategist,
                                                view: TransactionsView,
                                         @IOScheduler scheduler: Scheduler,
                                                @UIScheduler uiScheduler: Scheduler)
            : TransactionsPresenter = TransactionsPresenterImpl(getTransactions = getTransactions,
            coordinator = coordinator, strategist = strategist, view = view,
            ioScheduler = scheduler, uiScheduler = uiScheduler)

    @Provides fun providesTransactionsAdapter(): TransactionsAdapter = TransactionsAdapter()

    @Provides fun providesEmptyStateView(view: TransactionsFragment): EmptyStateView  = view

    @Provides fun providesLoadingView(view: TransactionsFragment): LoadingView = view

    @Provides fun providesErrorView(view: TransactionsFragment): ErrorStateView = view

    @Provides fun providesStrategist(view: TransactionsFragment): LifecycleOwner = view

    @Provides fun providesTransactionsView(view: TransactionsFragment): TransactionsView = view

    @Provides fun providesToogleRefreshView(view: TransactionsFragment): ToogleRefreshView = view

    @Provides fun providesNetworkingView(view: TransactionsFragment): NetworkingView = view

    @Provides fun providesPlaceHolder(view: TransactionsFragment): PlaceholderViewsManager {
        return PlaceholderViewsManager(loadingViewStub = view.state_view_loading,
                errorViewStub = view.state_view_error,
                emptyViewStub = view.state_view_empty,
                containerView = view.recyclerView)
    }

}

@Module
class TransactionsUseCaseModule {

    @Provides fun providesRegisterTransaction(transactionsRepository: TransactionsRepository)
            : RegisterTransaction = RegisterTransactionImpl(repository = transactionsRepository)

    @Provides fun providesGetTransactions(repository: TransactionsRepository)
            : GetTransactions = GetTransactionsImpl(repository = repository)

    @Provides fun providesTransactionsRepository(database: TransactionsDatabase,
                                                 networking: TransactionsNetworkingDatasource)
            : TransactionsRepository = TransactionsRepositoryImpl(database = database,
            networking = networking)

    @Provides fun providesTransactionsDatabase(): TransactionsDatabase = TransactionsDatabaseImpl()

    @Provides fun providesNetworkingDatasource(transactionsApi: TransactionsApi)
            : TransactionsNetworkingDatasource = TransactionsNetworkingDatasourceImpl(transactionsApi = transactionsApi)

    @Provides fun providesTransactionsApi(@Named("retrofitTransaction") retrofit: Retrofit)
            : TransactionsApi = retrofit.create(TransactionsApi::class.java)

}