package br.com.wagnerrodrigues.starwarsstore.presentation.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.wagnerrodrigues.starwarsstore.presentation.presenter.PurchasesPresenter
import br.com.wagnerrodrigues.starwarsstore.presentation.view.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_main.*
import starwarsstore.wagnerrodrigues.com.br.starwarsstore.R

class PurchasesFragment : Fragment() {

    private val presenter: PurchasesPresenter = PurchasesPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter.registerEvents()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_main, container, false)
    }

    override fun onDestroy() {
        presenter.unregisterEvents()
        super.onDestroy()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.prepareProducts()
        fb_cart.setOnClickListener {
            (activity as MainActivity).showCart()
        }
    }

    fun onProductAddedToCart(quantity : Int) {
        Toast.makeText(this.context, getString(R.string.product_added_to_cart, quantity.toString()), Toast.LENGTH_SHORT).show()
    }

}