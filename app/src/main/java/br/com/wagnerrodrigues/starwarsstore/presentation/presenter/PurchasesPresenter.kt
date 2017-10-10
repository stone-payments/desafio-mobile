package br.com.wagnerrodrigues.starwarsstore.presentation.presenter

import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import br.com.wagnerrodrigues.starwarsstore.domain.interactor.PurchasesInteractor
import br.com.wagnerrodrigues.starwarsstore.presentation.event.PurchasesPreparedEvent
import br.com.wagnerrodrigues.starwarsstore.presentation.view.adapter.PurchasesAdapter
import br.com.wagnerrodrigues.starwarsstore.presentation.view.fragment.PurchasesFragment
import kotlinx.android.synthetic.main.fragment_purchases.*
import org.greenrobot.eventbus.Subscribe

/**
 * Presenter da lista de compras
 */

class PurchasesPresenter(private val fragment: PurchasesFragment) : Presenter() {

    private val interactor : PurchasesInteractor = PurchasesInteractor()

    fun preparePurchases() {
        interactor.preparePurchases()
    }

    @Subscribe
    fun onPurchasesPrepared(purchasesPreparedEvent: PurchasesPreparedEvent){
        purchasesPreparedEvent.transactions.forEach {
            it.value = StringBuilder(it.value).insert(it.value?.length!!.minus(2), ".").toString()
        }
        fragment.rv_purchases_list.adapter = PurchasesAdapter(fragment.context, purchasesPreparedEvent.transactions?.toMutableList())
        fragment.rv_purchases_list.layoutManager = LinearLayoutManager(fragment.context, LinearLayout.VERTICAL, false)
    }
}