package br.com.ygorcesar.desafiostone.view.item

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ygorcesar.desafiostone.R
import br.com.ygorcesar.desafiostone.data.ShoppingCart
import br.com.ygorcesar.desafiostone.data.toast
import br.com.ygorcesar.desafiostone.databinding.FragmentDetailItemBinding
import br.com.ygorcesar.desafiostone.model.Item
import br.com.ygorcesar.desafiostone.viewmodel.ItemViewModel


class ItemDetailFragment : android.support.v4.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: android.os.Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentDetailItemBinding>(inflater, R.layout.fragment_detail_item, container, false)

        arguments?.let {
            val item = Item(it.getString(MainFragment.ITEM_TITLE), it.getDouble(MainFragment.ITEM_PRICE),
                    "", it.getString(MainFragment.ITEM_SELLER), it.getString(MainFragment.ITEM_THUMB_URL), "")
            binding.item = ItemViewModel(item)
        }

        binding.btnAddToCart.setOnClickListener {
           ShoppingCart.Companion.instance.addItem(binding.item?.getItem())
            toast(R.string.item_added)
            activity.onBackPressed()
        }
        return binding.root
    }
}