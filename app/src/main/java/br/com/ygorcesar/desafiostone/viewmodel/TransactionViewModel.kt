package br.com.ygorcesar.desafiostone.viewmodel

import android.databinding.BaseObservable
import br.com.ygorcesar.desafiostone.data.toCurrencyBRL
import br.com.ygorcesar.desafiostone.model.Transaction

class TransactionViewModel(private var transaction: Transaction) : BaseObservable() {

    fun setTransaction(transaction: Transaction) {
        this.transaction = transaction
        notifyChange()
    }

    fun getValue() = "Valor: ${this.transaction.value.toCurrencyBRL()}"

    fun getCardHolderName() = "Portador CC: ${this.transaction.card_holder_name}"

    fun getDate() = this.transaction.date

    fun getCardLastDigits() = "CC: ...${this.transaction.ccLastDigits}"
}