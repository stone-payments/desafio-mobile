package br.com.wagnerrodrigues.starwarsstore.domain.event

import br.com.wagnerrodrigues.starwarsstore.domain.entity.Transaction

class PaymentSuccessfulEvent(var transaction: Transaction)