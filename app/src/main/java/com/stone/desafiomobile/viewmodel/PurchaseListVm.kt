package com.stone.desafiomobile.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.stone.desafiomobile.database.PurchaseLogDAO
import com.stone.desafiomobile.model.PurchaseLog
import javax.inject.Inject

class PurchaseListVm() : ViewModel() {

    @Inject
    lateinit var purchaseLogDAO: PurchaseLogDAO

    val purchaseLogList: LiveData<List<PurchaseLog>>
        get() {
            return purchaseLogDAO.listAll()
        }
}