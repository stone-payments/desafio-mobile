package br.com.wagnerrodrigues.starwarsstore.presentation.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.wagnerrodrigues.starwarsstore.domain.entity.CartItem
import br.com.wagnerrodrigues.starwarsstore.domain.entity.Transaction
import kotlinx.android.synthetic.main.purchase_card_item.view.*
import starwarsstore.wagnerrodrigues.com.br.starwarsstore.R
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class PurchasesAdapter constructor(private val context: Context, private var purchases: MutableList<Transaction>?) : RecyclerView.Adapter<PurchasesAdapter.PurchasesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PurchasesViewHolder? {
        val layoutInflater = LayoutInflater.from(context)
        return PurchasesViewHolder(layoutInflater.inflate(R.layout.purchase_card_item, parent, false))
    }

    override fun onBindViewHolder(holder: PurchasesViewHolder, position: Int) {
        holder.bind(purchases?.get(position))
    }

    override fun getItemCount(): Int {
        return purchases!!.count()
    }

    fun remove(cartItem: CartItem?) {
        purchases!!.removeAll{it.equals(cartItem)}
        notifyDataSetChanged()
    }

    fun update(cards: MutableList<CartItem>?) {
        if(cards != null){
            this.purchases = purchases
        }
        else{
            this.purchases = mutableListOf()
        }
        this.notifyDataSetChanged()
    }

    class PurchasesViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bind(purchase: Transaction?) {
            val name = itemView.tx_card_holder_name
            val cardLastDigits = itemView.tx_card_last_digits
            val date = itemView.tx_date
            val value = itemView.tx_total_value

            name.text = purchase?.cardHolderName
            cardLastDigits.text = purchase?.lastDigits
            date.text = purchase?.dateTime.toString()
            value.text = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(BigDecimal(purchase?.value))

        }
    }

}