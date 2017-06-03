package com.partiufast.mercadodoimperador.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.partiufast.mercadodoimperador.model.BillHistory
import com.partiufast.mercadodoimperador.R
import kotlinx.android.synthetic.main.bill_card.view.*

class BillAdapter(val bills: List<BillHistory>) :
        RecyclerView.Adapter<BillAdapter.BillViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bill_card, parent, false)
        return BillViewHolder(view)
    }

    override fun onBindViewHolder(holder: BillViewHolder, position: Int) {
        holder.bindBill(bills[position])
    }

    override fun getItemCount(): Int = bills.size

    class BillViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindBill(bill: BillHistory) {
            with(bill) {
                itemView.bill_text_view.text = printStringBill(bill)
            }
        }
        fun printStringBill(bill: BillHistory): String{
            return "Data: "+bill.date+ '\n'+"Nome do consumidor: " + bill.customerName+'\n'+
                    "terminação do cartão: " + bill.cardLastNumbers+'\n'+ "Valor: " + bill.value
        }
    }


}

