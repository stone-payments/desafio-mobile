package com.stone.desafiomobile.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.stone.desafiomobile.database.PurchaseLogDAO
import com.stone.desafiomobile.model.PurchaseLog
import javax.inject.Inject

/**
 * Viewmodel do historico de compras
 */
class PurchaseListVm() : ViewModel() {

    @Inject
    lateinit var purchaseLogDAO: PurchaseLogDAO

    /**
     * Retorna o historico do banco
     */
    val purchaseLogList: LiveData<List<PurchaseLog>>
        get() {
            return purchaseLogDAO.listAll()
        }
}