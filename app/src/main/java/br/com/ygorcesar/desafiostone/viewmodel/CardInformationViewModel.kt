package br.com.ygorcesar.desafiostone.viewmodel

import android.databinding.BaseObservable
import android.view.View
import br.com.ygorcesar.desafiostone.model.CardInformation

class CardInformationViewModel(private var card: CardInformation, private val onClick: () -> Unit) : BaseObservable() {

    fun setCard(card: CardInformation) {
        this.card = card
    }

    fun getCard() = card

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

    fun bindCheckouClick(): View.OnClickListener {
        return View.OnClickListener { onClick() }
    }

    fun validCard(): Boolean {
        return !card.card_number.isNullOrEmpty() && !card.card_holder_name.isNullOrEmpty()
                && !card.cvv.toString().isNullOrEmpty() && card.cvv > 99
                && !card.exp_date.isNullOrEmpty()
    }
}