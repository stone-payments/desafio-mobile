package com.stone.desafiomobile.viewmodel

import android.arch.lifecycle.ViewModel
import com.stone.desafiomobile.R
import com.stone.desafiomobile.database.PurchaseLogDAO
import com.stone.desafiomobile.model.Purchase
import com.stone.desafiomobile.model.PurchaseLog
import com.stone.desafiomobile.network.RestRepository
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import java.util.*
import javax.inject.Inject


class CheckoutVm() : ViewModel() {

    @Inject
    lateinit var restRepository: RestRepository

    @Inject
    lateinit var purchaseLogDAO: PurchaseLogDAO

    fun buyProducts(purchase: Purchase, callback: (Int) -> Unit) {
        restRepository.BuyProducts(purchase, {
            saveTransaction(purchase)
            callback(R.string.purchase_success)
        }, {
            callback(R.string.purchase_fail)
        })
    }

    fun saveTransaction(purchase: Purchase) {
        launch(CommonPool) {
            val purchaseLog = PurchaseLog(null, purchase.value, Date(), purchase.cardNumber.takeLast(4), purchase.cardHolderName)
            purchaseLogDAO.insert(purchaseLog)
        }
    }
}