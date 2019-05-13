package br.com.stone.vianna.starstore.feature.itemList

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.stone.vianna.starstore.entity.Item
import br.com.stone.vianna.starstore.R
import br.com.stone.vianna.starstore.extensions.toMoneyFormat
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_list_item.view.*

class ItemsAdapter(private val itemClick: (Item) -> Unit) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    private var items: List<Item> = mutableListOf()


    fun updateItems(items: List<Item>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.view_list_item, parent, false), this.itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View, private val itemClick: (Item) -> Unit) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Item) = with(itemView) {
            itemView.tv_item_name.text = item.title
            itemView.tv_item_price.text = item.price.toMoneyFormat()
            itemView.setOnClickListener { itemClick(item) }

            Picasso.get()
                    .load(item.thumbnailHd)
                    .fit()
                    .centerCrop()
                    .into(itemView.item_image)
        }
    }

}