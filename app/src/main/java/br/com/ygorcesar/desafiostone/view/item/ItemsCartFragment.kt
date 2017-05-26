package br.com.ygorcesar.desafiostone.view.item

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ygorcesar.desafiostone.R
import br.com.ygorcesar.desafiostone.data.ShoppingCart
import br.com.ygorcesar.desafiostone.view.transaction.CheckoutFragment
import kotlinx.android.synthetic.main.empty_view.*
import kotlinx.android.synthetic.main.fragment_main.*


class ItemsCartFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ShoppingCart.Companion.instance.items.let {
            if (it.isEmpty()) {
                ln_empty_view.visibility = View.VISIBLE
            } else {
                rv_items.setHasFixedSize(true)
                rv_items.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                rv_items.adapter = AdapterItemCart(it)

                fab.apply {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment, CheckoutFragment())
                                .commit()
                    }
                }
            }
        }
    }
}