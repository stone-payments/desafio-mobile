package com.example.pharol.temminstore.transaction

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel


class TransactionViewModel(val transactionRepo: TransactionRepository) : ViewModel(){

    fun getTransactions(): LiveData<List<Transaction>> {
        return transactionRepo.getAll()
    }

    fun save(transaction: Transaction){
        transactionRepo.pay(transaction)
    }

}