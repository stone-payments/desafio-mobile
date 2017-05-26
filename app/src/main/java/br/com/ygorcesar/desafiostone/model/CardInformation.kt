package br.com.ygorcesar.desafiostone.model

data class CardInformation(var card_number: String, var value: Double, var cvv: Int,
                           var card_holder_name: String, var exp_date: String)