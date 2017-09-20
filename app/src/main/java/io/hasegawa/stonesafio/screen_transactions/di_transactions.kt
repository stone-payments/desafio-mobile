package io.hasegawa.stonesafio.screen_transactions

import dagger.Component
import dagger.Module
import dagger.Provides
import io.hasegawa.presentation.screen_transactions.TransactionsGetUC
import io.hasegawa.presentation.screen_transactions.TransactionsPresenter
import io.hasegawa.stonesafio.di.BaseDIComponent
import io.hasegawa.stonesafio.domain.payment.TransactionRepository
import javax.inject.Scope


@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class TransactionsControllerScope

@Module
class TransactionsDIModule(private val controller: TransactionsController) {
    @Provides
    @TransactionsControllerScope
    fun providePresenter(transactionRepository: TransactionRepository): TransactionsPresenter {
        val navigator = TransactionNavigator(controller)
        val getUC = TransactionsGetUC(transactionRepository)
        return TransactionsPresenter(navigator, getUC)
    }
}

@TransactionsControllerScope
@Component(modules = arrayOf(TransactionsDIModule::class),
        dependencies = arrayOf(BaseDIComponent::class))
interface TransactionsDIComponent {
    companion object {
        fun initialize(controller: TransactionsController): TransactionsDIComponent {
            return DaggerTransactionsDIComponent.builder()
                    .transactionsDIModule(TransactionsDIModule(controller))
                    .baseDIComponent(BaseDIComponent.instance)
                    .build()
        }
    }

    fun getPresenter(): TransactionsPresenter
}