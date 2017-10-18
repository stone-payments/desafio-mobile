package jb.project.starwarsshoppinglist.orderHistory.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import jb.project.starwarsshoppinglist.R
import jb.project.starwarsshoppinglist.model.Purchase


/**
 * Created by Jb on 17/10/2017.
 */
class OrderHistoryAdapter(orderList: OrderedRealmCollection<Purchase>) : RealmRecyclerViewAdapter<Purchase, OrderHistoryAdapter.ItemViewHolder>(orderList, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder? {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_order_history, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder?, position: Int) {
        val item = data!![position]
        val itemViewHolder = holder as ItemViewHolder
        itemViewHolder.loadItem(item)
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtName = itemView.findViewById<TextView>(R.id.txt_name) as TextView
        private val txtDate = itemView.findViewById<TextView>(R.id.text_date) as TextView
        private val textPrice = itemView.findViewById<TextView>(R.id.text_price) as TextView
        private val layoutOrder = itemView.findViewById<RelativeLayout>(R.id.layout_order) as RelativeLayout

        fun loadItem(item: Purchase) {

            if (adapterPosition % 2 == 0) {
                layoutOrder.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.background_gray))
            }


            txtName.text = item.cardHolderName
            txtDate.text = item.datePurchased.toString()
            textPrice.text = "R$ " + String.format("%.2f", item.value?.div(100.0))
        }
    }
}