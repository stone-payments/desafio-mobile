package com.example.pharol.temminstore.transaction

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

class TransactionViewModelFactory
@Inject constructor(val transactionRepo: TransactionRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        return TransactionViewModel(transactionRepo) as T
    }

}