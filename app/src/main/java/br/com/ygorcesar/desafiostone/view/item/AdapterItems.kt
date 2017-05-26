package br.com.ygorcesar.desafiostone.view.item

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.ygorcesar.desafiostone.R
import br.com.ygorcesar.desafiostone.databinding.AdapterItemBinding
import br.com.ygorcesar.desafiostone.model.Item
import br.com.ygorcesar.desafiostone.viewmodel.ItemViewModel
import java.util.*

class AdapterItems(private var mItems: ArrayList<Item>?, val onItemClick: (item: Item) -> Unit) : RecyclerView.Adapter<AdapterItems.ItemViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ItemViewHolder {
        val binding = DataBindingUtil.inflate<AdapterItemBinding>(LayoutInflater.from(viewGroup.context),
                R.layout.adapter_item, viewGroup, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(itemVH: ItemViewHolder, pos: Int) = itemVH.bind(getItem(pos))

    override fun getItemCount() = mItems?.size ?: 0

    fun getItem(position: Int) = mItems?.get(position)

    inner class ItemViewHolder constructor(private val mBinding: AdapterItemBinding) : RecyclerView.ViewHolder(mBinding.root) {

        init {
            this.mBinding.root.setOnClickListener { getItem(adapterPosition)?.let { onItemClick(it) } }
        }

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
