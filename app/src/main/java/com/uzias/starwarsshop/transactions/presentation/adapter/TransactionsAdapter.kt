package com.uzias.starwarsshop.transactions.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uzias.starwarsshop.R

import com.uzias.starwarsshop.transactions.presentation.model.PresentationTransaction
import kotlinx.android.synthetic.main.list_item_transactions.view.*

class TransactionsAdapter: RecyclerView.Adapter<TransactionViewHolder>() {
    private val transactions = mutableListOf<PresentationTransaction>()

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.itemView.textViewName.text = transaction.name
        holder.itemView.textViewDate.text = transaction.getDateFormatted()
        holder.itemView.textViewCard.text = transaction.lastFourDigitsCard
        holder.itemView.textViewValue.text = holder.itemView.context.getString(R.string.label_price,
                transaction.getValueFormatted())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder
            = TransactionViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_transactions, parent, false))

    fun setTransactions(list: List<PresentationTransaction>){
        transactions.removeAll(transactions)
        transactions.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        transactions.removeAll(transactions)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = transactions.size

}

class TransactionViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
