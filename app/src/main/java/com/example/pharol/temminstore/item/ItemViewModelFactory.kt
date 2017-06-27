package com.example.pharol.temminstore.item

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.pharol.temminstore.shoppingcart.ShoppingCartRepository
import javax.inject.Inject


class ItemViewModelFactory @Inject constructor(val itemRepository: ItemRepository,
                                               val cartRepository: ShoppingCartRepository): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        return ItemViewModel(itemRepository,cartRepository) as T
    }

}