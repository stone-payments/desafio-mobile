package com.stone.desafiomobile.viewmodel

import android.arch.lifecycle.ViewModel
import com.stone.desafiomobile.R
import com.stone.desafiomobile.model.Purchase
import com.stone.desafiomobile.network.RestRepository
import javax.inject.Inject


class CheckoutVm() : ViewModel() {

    @Inject
    lateinit var restRepository: RestRepository

    fun buyProducts(purchase: Purchase, callback: (Int) -> Unit) {
        restRepository.BuyProducts(purchase, {
            callback(R.string.purchase_success)
        }, {
            callback(R.string.purchase_fail)
        })
    }
}