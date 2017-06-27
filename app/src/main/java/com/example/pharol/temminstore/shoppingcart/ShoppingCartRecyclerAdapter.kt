package com.example.pharol.temminstore.shoppingcart

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.pharol.temminstore.R
import com.example.pharol.temminstore.util.toMoneyString
import com.squareup.picasso.Picasso
import java.math.BigDecimal

class ShoppingCartRecyclerAdapter(var shptCartItemList: List<ShoppingCart>, val onclickListener: onButtonsClickListener) : RecyclerView.Adapter<ShoppingCartRecyclerAdapter.ViewHolder>() {

    interface onButtonsClickListener{
        fun onClickMinus(cart: ShoppingCart)
        fun onClickPlus(cart: ShoppingCart)
    }

    override fun onBindViewHolder(cardView: ViewHolder?, position: Int) {
        val cart: ShoppingCart = shptCartItemList[position]
        cardView?.tv_cartItemTitle?.text = cart.item.title
        cardView?.tv_cartItemPrice?.text = cart.item.price.toMoneyString()
        cardView?.tv_cartItemQuantity?.text = cart.qtdItems.toString()
        cardView?.tv_cartItemPriceTotal?.text = (cart.item.price.multiply(BigDecimal(cart.qtdItems))).toMoneyString()
        cardView?.bt_plus?.setOnClickListener({
            onclickListener.onClickPlus(cart)
        })
        cardView?.bt_minus?.setOnClickListener({
            onclickListener.onClickMinus(cart)
        })

        Picasso.with(cardView?.itemView?.context?.applicationContext).
                load(cart.item.thumbnailHd).error(R.drawable.ic_not_found).resize(160,160).into(cardView?.iv_ItemPhoto)
    }

    override fun getItemCount() = shptCartItemList.size

    override fun onCreateViewHolder(parent: ViewGroup?, p1: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent?.context).inflate(R.layout.v_item_shopping_cart_view, parent,false)
        return ShoppingCartRecyclerAdapter.ViewHolder(view)
    }

    class ViewHolder(shpCartView: View) : RecyclerView.ViewHolder(shpCartView) {
        val tv_cartItemTitle       by lazy { shpCartView.findViewById(R.id.tv_itemcart_title) as TextView }
        val tv_cartItemPrice       by lazy { shpCartView.findViewById(R.id.tv_itemcart_price) as TextView }
        val tv_cartItemPriceTotal  by lazy { shpCartView.findViewById(R.id.tv_itemcart_pricetotal) as TextView }
        val tv_cartItemQuantity    by lazy { shpCartView.findViewById(R.id.tv_itemcart_qtt) as TextView }
        val iv_ItemPhoto           by lazy { shpCartView.findViewById(R.id.iv_cartitem_photo) as ImageView }
        val bt_plus                by lazy { shpCartView.findViewById(R.id.bt_plus) as Button }
        val bt_minus               by lazy { shpCartView.findViewById(R.id.bt_minus) as Button}
    }

}