package br.com.wagnerrodrigues.starwarsstore.presentation.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.wagnerrodrigues.starwarsstore.presentation.presenter.PurchasesPresenter
import br.com.wagnerrodrigues.starwarsstore.presentation.view.activity.MainActivity
import kotlinx.android.synthetic.main.app_bar_main.*
import starwarsstore.wagnerrodrigues.com.br.starwarsstore.R

class PurchasesFragment : Fragment() {

    private val presenter: PurchasesPresenter = PurchasesPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter.registerEvents()
        (activity as MainActivity).shouldDisplayHomeUp()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_purchases, container, false)
    }

    override fun onDestroy() {
        presenter.unregisterEvents()
        super.onDestroy()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity.toolbar.title = getString(R.string.purchase_title);
        presenter.preparePurchases()
    }

}