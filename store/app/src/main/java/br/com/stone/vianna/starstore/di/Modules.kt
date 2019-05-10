package br.com.stone.cryptowallet.di

import br.com.stone.vianna.starstore.AppDatabase
import org.koin.dsl.module

val daoModule = module {
    single { get<AppDatabase>().itemDao() }
    single { get<AppDatabase>().transactionDao() }
}
