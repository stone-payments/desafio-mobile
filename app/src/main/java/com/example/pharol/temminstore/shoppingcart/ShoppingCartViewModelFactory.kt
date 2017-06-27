package com.example.pharol.temminstore.shoppingcart

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.pharol.temminstore.shoppingcart.ShoppingCartRepository
import com.example.pharol.temminstore.shoppingcart.ShoppingCartViewModel
import javax.inject.Inject

class ShoppingCartViewModelFactory @Inject constructor(
        val cartRepository: ShoppingCartRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        return ShoppingCartViewModel(cartRepository) as T
    }

}