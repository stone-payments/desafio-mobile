package br.com.wagnerrodrigues.starwarsstore.presentation.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import br.com.wagnerrodrigues.starwarsstore.domain.entity.Product
import br.com.wagnerrodrigues.starwarsstore.presentation.event.ButtonAddToCartPressedEvent
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_product.view.*
import org.greenrobot.eventbus.EventBus
import starwarsstore.wagnerrodrigues.com.br.starwarsstore.R
import java.text.NumberFormat
import java.util.*

class CardsProductAdapter constructor(private val context: Context, private var cards: MutableList<Product>?) : RecyclerView.Adapter<CardsProductAdapter.CardsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CardsViewHolder? {
        val layoutInflater = LayoutInflater.from(context)
        return CardsViewHolder(layoutInflater.inflate(R.layout.card_product, parent, false))
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        holder.bind(cards?.get(position))
    }

    override fun getItemCount(): Int {
        return cards!!.count()
    }

    class CardsViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bind(product: Product?) {
            val name = itemView.tx_product_name
            val thumb = itemView.iv_thumb
            val seller = itemView.tx_product_seller
            val price = itemView.tx_product_price

            name.text = product?.title
            seller.text = product?.seller
            price.text = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(product?.price)
            Picasso.with(itemView.context).load(product?.thumbnailHd).into(thumb)
            itemView.sp_quantity.adapter = ArrayAdapter<Int>(itemView.context,R.layout.support_simple_spinner_dropdown_item, mutableListOf(1,2,3,4,5,6,7,8,9,10))

            itemView.bt_add_cart.setOnClickListener{
                EventBus.getDefault().post(ButtonAddToCartPressedEvent(product,Integer.parseInt(itemView.sp_quantity.selectedItem?.toString())))
            }


        }
    }
}
