package br.com.stone.vianna.starstore.view.shoppingCart

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.stone.vianna.starstore.extensions.toMoneyFormat
import br.com.stone.vianna.starstore.R
import br.com.stone.vianna.starstore.entity.Item
import kotlinx.android.synthetic.main.view_item_cart.view.*

class ShoppingCartAdapter : RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private val items: MutableList<Item>
    private val itemClick: (Item) -> Unit

    constructor(items: List<Item>, itemClick: (Item) -> Unit) : super() {
        this.itemClick = itemClick
        this.items = items.toMutableList()
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
