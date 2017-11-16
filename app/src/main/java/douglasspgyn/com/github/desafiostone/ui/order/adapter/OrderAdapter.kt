package douglasspgyn.com.github.desafiostone.ui.order.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import douglasspgyn.com.github.desafiostone.R
import douglasspgyn.com.github.desafiostone.business.model.Order
import douglasspgyn.com.github.desafiostone.common.extensions.inflate
import douglasspgyn.com.github.desafiostone.common.extensions.toCurrency
import kotlinx.android.synthetic.main.item_order.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Douglas on 13/11/17.
 */

class OrderAdapter(private val orders: List<Order>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): OrderViewHolder {
        return OrderViewHolder(parent?.inflate(R.layout.item_order))
    }

    override fun onBindViewHolder(holder: OrderViewHolder?, position: Int) {
        holder?.bind(orders[position])
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    class OrderViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(order: Order) {
            with(itemView) {
                val date = Date()
                date.time = order.date

                orderCardNumber.text = context.getString(R.string.order_card_number, order.cardNumber)
                orderCardHolderName.text = order.cardHolderName
                orderPrice.text = context.getString(R.string.total_price, order.value.toDouble().toCurrency())
                orderDateTime.text = SimpleDateFormat().format(date)
            }
        }
    }
}