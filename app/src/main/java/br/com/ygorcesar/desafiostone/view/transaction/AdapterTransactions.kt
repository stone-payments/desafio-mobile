package br.com.ygorcesar.desafiostone.view.transaction

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.ygorcesar.desafiostone.R
import br.com.ygorcesar.desafiostone.databinding.AdapterTransactionBinding
import br.com.ygorcesar.desafiostone.model.Transaction
import br.com.ygorcesar.desafiostone.viewmodel.TransactionViewModel

class AdapterTransactions(private var mItems: List<Transaction>?) : RecyclerView.Adapter<AdapterTransactions.TransactionViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): TransactionViewHolder {
        val binding = DataBindingUtil.inflate<AdapterTransactionBinding>(LayoutInflater.from(viewGroup.context),
                R.layout.adapter_transaction, viewGroup, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(itemVH: TransactionViewHolder, pos: Int) = itemVH.bind(getItem(pos))

    override fun getItemCount() = mItems?.size ?: 0

    fun getItem(position: Int) = mItems?.get(position)

    inner class TransactionViewHolder constructor(private val mBinding: AdapterTransactionBinding) : RecyclerView.ViewHolder(mBinding.root) {

        fun bind(transaction: Transaction?) {
            transaction?.let {
                if (mBinding.transaction == null) {
                    mBinding.transaction = TransactionViewModel(it)
                } else {
                    mBinding.transaction?.setTransaction(it)
                }
            }
        }
    }
}