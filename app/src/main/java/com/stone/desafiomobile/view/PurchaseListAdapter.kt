package com.stone.desafiomobile.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.stone.desafiomobile.R
import com.stone.desafiomobile.model.PurchaseLog
import java.text.SimpleDateFormat

class PurchaseListAdapter() : RecyclerView.Adapter<PurchaseListAdapter.ViewHolder>() {

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
        holder.mValueView.text = item.value.toString()
        val dateformat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        holder.mDateView.text = dateformat.format(item.date)
        holder.mLastDigitsView.text = item.lastCardDigits
        holder.mHolderNameView.text = item.cardHolderName
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

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
