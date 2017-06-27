package com.example.pharol.temminstore.transaction

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.pharol.temminstore.R
import com.example.pharol.temminstore.util.toMoneyString


class TransactionRecyclerAdapter(var listTransactions: List<Transaction>): RecyclerView.Adapter<TransactionRecyclerAdapter.ViewHolder>(){
    override fun onBindViewHolder(cardView: ViewHolder?, position: Int) {
        val transaction = listTransactions[position]
        cardView?.tv_transaction_id?.text = transaction.id.toString()
        cardView?.tv_card_number?.text = "**** **** **** " + transaction.card_number
        cardView?.tv_cardholder_name?.text = transaction.card_holder_name
        cardView?.tv_date?.text = transaction.date.toString()
        cardView?.tv_value?.text = transaction.value.toMoneyString()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent?.context).
                inflate(R.layout.v_item_transaction_view,parent,false))
    }

    override fun getItemCount() = listTransactions.size

    class ViewHolder(transactionView: View): RecyclerView.ViewHolder(transactionView) {
        val tv_transaction_id   by lazy { transactionView.findViewById(R.id.textView7) as TextView }
        val tv_card_number      by lazy { transactionView.findViewById(R.id.textView5) as TextView }
        val tv_cardholder_name  by lazy { transactionView.findViewById(R.id.textView8) as TextView }
        val tv_value            by lazy { transactionView.findViewById(R.id.textView6) as TextView }
        val tv_date             by lazy { transactionView.findViewById(R.id.textView4) as TextView }
    }

}