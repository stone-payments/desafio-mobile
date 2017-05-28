package br.com.ygorcesar.desafiostone.view.item

import android.databinding.DataBindingUtil
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ygorcesar.desafiostone.R
import br.com.ygorcesar.desafiostone.databinding.FragmentDetailItemBinding
import br.com.ygorcesar.desafiostone.model.Item
import br.com.ygorcesar.desafiostone.viewmodel.ItemViewModel
import com.google.gson.Gson


class ItemDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: android.os.Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentDetailItemBinding>(inflater, R.layout.fragment_detail_item, container, false)

        arguments?.let {
            val item = Gson().fromJson(it.getString(MainFragment.KEY_ITEM), Item::class.java)
            item?.let { binding.item = ItemViewModel(item, { activity.onBackPressed() }) }
        }
        return binding.root
    }
}