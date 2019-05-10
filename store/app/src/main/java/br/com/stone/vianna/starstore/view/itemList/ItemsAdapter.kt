package br.com.stone.vianna.starstore.view.itemList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.stone.vianna.starstore.entity.Item
import br.com.stone.vianna.starstore.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_list_item.view.*

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private val items: List<Item>
    private val itemClick: (Item, View) -> Unit

    constructor(items: List<Item>, itemClick: (Item, View) -> Unit) : super() {
        this.itemClick = itemClick
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.view_list_item, parent, false), this.itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View, private val itemClick: (Item, View) -> Unit) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Item) = with(itemView) {
            itemView.tv_item_name.text = item.title
            itemView.setOnClickListener { itemClick(item, itemView) }

            Picasso.get()
                    .load(item.thumbnailHd)
                    .fit()
                    .centerCrop()
                    .into(itemView.item_image)
        }
    }

}