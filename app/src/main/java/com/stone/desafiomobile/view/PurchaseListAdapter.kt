package com.stone.desafiomobile.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.stone.desafiomobile.R
import com.stone.desafiomobile.model.PurchaseLog
import com.stone.desafiomobile.utils.formatPriceReal
import java.text.SimpleDateFormat

/**
 * Adapter para popular o historico de compras
 */
class PurchaseListAdapter() : RecyclerView.Adapter<PurchaseListAdapter.ViewHolder>() {

    // itens para popular a lista
    var mValues: List<PurchaseLog> = ArrayList()
        set (new) {
            if (new != field) {
                field = new
                notifyDataSetChanged()
            }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_purchase_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues.get(position)
        holder.mValueView.text = item.value?.formatPriceReal()
        holder.mLastDigitsView.text = item.lastCardDigits
        holder.mHolderNameView.text = item.cardHolderName
        val dateformat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        holder.mDateView.text = dateformat.format(item.date)
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    /**
     * Classe que mantem as referencias do layout
     */
    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mValueView: TextView
        val mDateView: TextView
        val mLastDigitsView: TextView
        val mHolderNameView: TextView

        init {
            mValueView = mView.findViewById(R.id.value)
            mDateView = mView.findViewById(R.id.date)
            mLastDigitsView = mView.findViewById(R.id.last_digits)
            mHolderNameView = mView.findViewById(R.id.holder_name)
        }
    }
}
