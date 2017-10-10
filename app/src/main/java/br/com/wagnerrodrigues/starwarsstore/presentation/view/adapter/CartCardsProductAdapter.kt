package br.com.wagnerrodrigues.starwarsstore.presentation.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.wagnerrodrigues.starwarsstore.domain.entity.CartItem
import br.com.wagnerrodrigues.starwarsstore.presentation.event.ButtonRemoveFromCartPressedEvent
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_card_product.view.*
import org.greenrobot.eventbus.EventBus
import starwarsstore.wagnerrodrigues.com.br.starwarsstore.R
import java.text.NumberFormat
import java.util.*

class CartCardsProductAdapter constructor(private val context: Context, private var cards: MutableList<CartItem>?) : RecyclerView.Adapter<CartCardsProductAdapter.CardsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CardsViewHolder? {
        val layoutInflater = LayoutInflater.from(context)
        return CardsViewHolder(layoutInflater.inflate(R.layout.cart_card_product, parent, false))
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        holder.bind(cards?.get(position))
    }

    override fun getItemCount(): Int {
        return cards!!.count()
    }

    fun remove(cartItem: CartItem?) {
        cards!!.removeAll{it.equals(cartItem)}
        notifyDataSetChanged()
    }

    fun update(cards: MutableList<CartItem>?) {
        if(cards != null){
            this.cards = cards
        }
        else{
            this.cards = mutableListOf()
        }
        this.notifyDataSetChanged()
    }

    class CardsViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bind(cartItem: CartItem?) {
            val name = itemView.tx_cart_product_name
            val thumb = itemView.iv_cart_thumb
            val price = itemView.tx_cart_product_price

            name.text = cartItem?.productName
            price.text = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(cartItem?.price)
            Picasso.with(itemView.context).load(cartItem?.productThumb).into(thumb)
            itemView.tx_cart_quantity.text = cartItem?.quantity.toString()

            itemView.bt_remove_cart.setOnClickListener{
                EventBus.getDefault().post(ButtonRemoveFromCartPressedEvent(cartItem!!))
            }


        }
    }

}