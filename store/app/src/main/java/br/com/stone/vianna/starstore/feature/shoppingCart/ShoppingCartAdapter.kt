package br.com.stone.vianna.starstore.feature.shoppingCart

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.stone.vianna.starstore.helper.toMoneyFormat
import br.com.stone.vianna.starstore.R
import br.com.stone.vianna.starstore.entity.Item
import kotlinx.android.synthetic.main.view_item_cart.view.*

class ShoppingCartAdapter(private val itemClick: (Item) -> Unit) : RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {

    private var items: List<Item> = mutableListOf()

    fun updateItems(items: List<Item>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.view_item_cart, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], itemClick)

    override fun getItemCount() = items.size

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Item, itemClick: (Item) -> Unit) = with(itemView) {

            itemView.tv_service_value.text = item.price.toMoneyFormat()
            itemView.tv_services.text = item.title
            itemView.tv_seller_name.text = item.seller
            itemView.ib_delete.setOnClickListener {
                itemClick.invoke(item)
            }
        }
    }

}
