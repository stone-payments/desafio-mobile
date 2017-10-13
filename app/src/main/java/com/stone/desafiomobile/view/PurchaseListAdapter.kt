package com.stone.desafiomobile.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stone.desafiomobile.R
import com.stone.desafiomobile.model.PurchaseLog

class PurchaseListAdapter(private val mValues: List<PurchaseLog>) : RecyclerView.Adapter<PurchaseListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_purchase_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues.get(position)
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {


        init {
            //mIdView = mView.findViewById(R.id.id) as TextView

        }
    }
}
