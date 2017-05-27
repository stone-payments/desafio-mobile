package br.com.ygorcesar.desafiostone.viewmodel

import android.databinding.BaseObservable
import br.com.ygorcesar.desafiostone.model.CardInformation

class CardInformationViewModel(private var card: CardInformation) : BaseObservable() {

    fun setCard(card: CardInformation) {
        this.card = card
    }

    fun getNumber() = card.card_number

}