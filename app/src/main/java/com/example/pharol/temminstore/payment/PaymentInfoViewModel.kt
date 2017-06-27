package com.example.pharol.temminstore.payment

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.pharol.temminstore.shoppingcart.ShoppingCartRepository
import com.example.pharol.temminstore.transaction.Transaction
import com.example.pharol.temminstore.transaction.TransactionRepository
import java.math.BigDecimal
import java.util.*


class PaymentInfoViewModel(val cartRepository: ShoppingCartRepository,
                           val transactionRepo: TransactionRepository): ViewModel(){

    var value = BigDecimal.ZERO

    fun pay(number: String, cvv: String, name: String, date: Date){
       transactionRepo.pay(Transaction(0, number, value, cvv, name, date))
    }

    fun getTotalCart(): LiveData<BigDecimal> = cartRepository.getCartTotal()
}