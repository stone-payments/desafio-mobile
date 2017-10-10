package br.com.wagnerrodrigues.starwarsstore.presentation.event

import br.com.wagnerrodrigues.starwarsstore.domain.entity.Transaction

class PurchasesPreparedEvent(var transactions : List<Transaction>)