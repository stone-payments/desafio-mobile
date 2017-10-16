package jb.project.starwarsshoppinglist.cart.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import jb.project.starwarsshoppinglist.R
import jb.project.starwarsshoppinglist.cart.`interface`.ItemClickListener
import jb.project.starwarsshoppinglist.model.Cart


/**
 * Created by joao.neto on 16/10/2017.
 */
class AdapterItemsRecycler(itemList: OrderedRealmCollection<Cart>) : RealmRecyclerViewAdapter<Cart, AdapterItemsRecycler.ItemViewHolder>(itemList, true) {

    private lateinit var listener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder? {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_product_checkout, parent, false)
        return ItemViewHolder(view)
    }

    fun setItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: ItemViewHolder?, position: Int) {
        val item = data!![position]
        val itemViewHolder = holder as ItemViewHolder
        itemViewHolder.loadItem(item, listener)
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val btnDelete: ImageButton = view.findViewById(R.id.btn_delete)
        val imageProduct = itemView.findViewById<ImageView>(R.id.img_thumbnail) as ImageView
        val textViewTittle = itemView.findViewById<TextView>(R.id.text_tittle) as TextView
        val textPrice = itemView.findViewById<TextView>(R.id.text_price) as TextView
        val spinnerQuantity = itemView.findViewById<Spinner>(R.id.spinner_quantity) as Spinner

        fun loadItem(item: Cart, clickListener: ItemClickListener?) {
            textViewTittle.setText(item.title)

            spinnerQuantity.adapter = ArrayAdapter<Int>(itemView.context, R.layout.support_simple_spinner_dropdown_item, mutableListOf(0, 1, 2, 3, 4, 5, 6))
            item.quantity?.let(spinnerQuantity::setSelection)


            Picasso.with(itemView.context).load(item.thumbnailHd).fit().into(imageProduct, object : Callback {
                override fun onSuccess() {}
                override fun onError() {
                    imageProduct.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.img_not_available))
                }
            })

            textPrice.text = "R$ " + String.format("%.2f", item.price?.div(100.0))
            btnDelete.setImageResource(R.drawable.ic_delete)

            btnDelete.setOnClickListener {
                clickListener?.onClick(item.title)
            }
        }
    }


}