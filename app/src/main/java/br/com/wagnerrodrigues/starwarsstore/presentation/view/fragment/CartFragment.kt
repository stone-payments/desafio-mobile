package br.com.wagnerrodrigues.starwarsstore.presentation.view.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.wagnerrodrigues.starwarsstore.presentation.presenter.CartPresenter
import br.com.wagnerrodrigues.starwarsstore.presentation.view.activity.MainActivity
import br.com.wagnerrodrigues.starwarsstore.presentation.view.adapter.CartCardsProductAdapter
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_cart.*
import starwarsstore.wagnerrodrigues.com.br.starwarsstore.R


/**
 * Fragment do carrinho de comprar
 */
class CartFragment : Fragment() {

    private val presenter: CartPresenter = CartPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).shouldDisplayHomeUp()
        presenter.registerEvents()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onDestroy() {
        presenter.unregisterEvents()
        super.onDestroy()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_product_list.adapter = CartCardsProductAdapter(context, mutableListOf())
        presenter.prepareCart()
        activity.toolbar.title = getString(R.string.cart_title);

        bt_payment.isEnabled = rv_product_list.adapter.itemCount > 0

        bt_payment.setOnClickListener{
            val paymentFragment = Fragment.instantiate(context, PaymentFragment::class.java.name) as PaymentFragment
            val bundle = Bundle()
            bundle.putString("totalPurchase", tx_total_purchase.text.toString())
            paymentFragment.arguments = bundle

            val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
            fragmentTransaction
                    .replace(R.id.main_layout, paymentFragment)
                    .addToBackStack(paymentFragment::class.java.name)
                    .commit()
        }

    }
}
