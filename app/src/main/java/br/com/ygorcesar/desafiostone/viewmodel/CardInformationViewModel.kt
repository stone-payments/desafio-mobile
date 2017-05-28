package br.com.ygorcesar.desafiostone.viewmodel

import android.databinding.BaseObservable
import android.view.View
import br.com.ygorcesar.desafiostone.data.ApiDesafioMobile
import br.com.ygorcesar.desafiostone.data.ShoppingCart
import br.com.ygorcesar.desafiostone.data.formatToBrasil
import br.com.ygorcesar.desafiostone.data.lastFourDigits
import br.com.ygorcesar.desafiostone.model.CardInformation
import br.com.ygorcesar.desafiostone.model.Transaction
import com.vicpin.krealmextensions.queryLast
import com.vicpin.krealmextensions.save
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class CardInformationViewModel(private var card: CardInformation, private val onClick: () -> Unit) : BaseObservable() {

    fun setCard(card: CardInformation) {
        this.card = card
        notifyChange()
    }

    fun getNumber() = card.card_number

    fun setNumber(number: String) {
        card.card_number = number
    }

    fun getHolder() = card.card_holder_name

    fun setHolder(holder: String) {
        card.card_holder_name = holder
    }

    fun getCvv() = if (card.cvv > 0) card.cvv.toString() else ""

    fun setCvv(cvv: String) {
        cvv.toIntOrNull()?.let {
            card.cvv = it
        }
    }

    fun getExpDate() = card.exp_date

    fun setExpDate(expDate: String) {
        card.exp_date = expDate
    }

    fun bindCheckouClick() = View.OnClickListener { onClick() }

    fun validCard(): Boolean {
        return !card.card_number.isNullOrEmpty() && !card.card_holder_name.isNullOrEmpty()
                && !card.cvv.toString().isNullOrEmpty() && card.cvv > 99
                && !card.exp_date.isNullOrEmpty()
    }

    fun checkoutTransaction(onNext: () -> Unit = {}, onError: () -> Unit = {}, onComplete: () -> Unit) {
        ApiDesafioMobile().api.checkoutOrder(card)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onNext() },
                        { onError() },
                        {
                            onComplete()
                            val t = Transaction().queryLast()
                            val id = if (t != null) t.id + 1 else 1
                            Transaction(id, card.value, Date().formatToBrasil(), card.card_number.lastFourDigits(), card.card_holder_name).save()
                            ShoppingCart.Companion.instance.clear()
                        })
    }
}