package br.com.ygorcesar.desafiostone.view.item

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ygorcesar.desafiostone.R
import br.com.ygorcesar.desafiostone.data.*
import br.com.ygorcesar.desafiostone.view.transaction.CheckoutFragment
import br.com.ygorcesar.desafiostone.viewmodel.ItemViewModel
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
                showEmptyView()
            } else {
                rv_items.layoutLinear()
                rv_items.adapter = AdapterItemCart(it, { item ->
                    main_container.snack(R.string.remove_item, R.string.action_yes, f = {
                        ItemViewModel(item).removeItem()
                        rv_items.adapter?.notifyDataSetChanged()
                    })
                })

                fab.apply { setOnClickListener { replace(CheckoutFragment(), R.id.fragment) } }
            }
        }
    }
}