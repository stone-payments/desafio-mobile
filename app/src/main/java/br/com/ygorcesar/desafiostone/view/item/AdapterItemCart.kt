package br.com.ygorcesar.desafiostone.view.item

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.ygorcesar.desafiostone.R
import br.com.ygorcesar.desafiostone.databinding.AdapterItemCartBinding
import br.com.ygorcesar.desafiostone.model.Item
import br.com.ygorcesar.desafiostone.viewmodel.ItemViewModel


class AdapterItemCart (private var mItems: List<Item>?) : RecyclerView.Adapter<AdapterItemCart.ItemViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ItemViewHolder {
        val binding = DataBindingUtil.inflate<AdapterItemCartBinding>(LayoutInflater.from(viewGroup.context),
                R.layout.adapter_item_cart, viewGroup, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(itemVH: ItemViewHolder, pos: Int) = itemVH.bind(getItem(pos))

    override fun getItemCount() = mItems?.size ?: 0

    fun getItem(position: Int) = mItems?.get(position)

    inner class ItemViewHolder constructor(private val mBinding: AdapterItemCartBinding) : RecyclerView.ViewHolder(mBinding.root) {

        fun bind(item: Item?) {
            item?.let {
                if (mBinding.item == null) {
                    mBinding.item = ItemViewModel(it)
                } else {
                    mBinding.item?.setItem(it)
                }
            }
        }
    }
}