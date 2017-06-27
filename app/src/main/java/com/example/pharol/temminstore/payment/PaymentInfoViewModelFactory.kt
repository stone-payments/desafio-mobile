package com.example.pharol.temminstore.payment

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.pharol.temminstore.shoppingcart.ShoppingCartRepository
import com.example.pharol.temminstore.transaction.TransactionRepository
import javax.inject.Inject

class PaymentInfoViewModelFactory
@Inject constructor(val cartRepository: ShoppingCartRepository,
                    val transactionRepo: TransactionRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        return PaymentInfoViewModel(cartRepository, transactionRepo) as T
    }

}