package com.example.pharol.temminstore.item

import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.pharol.temminstore.R
import com.example.pharol.temminstore.shoppingcart.ShoppingCartRepository
import com.example.pharol.temminstore.util.toMoneyString
import com.squareup.picasso.Picasso
import javax.inject.Inject


class ItemRecyclerAdapter(var itemList: List<Item>, val onClickListener: onClickAddToCart) : RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder>() {

    @Inject lateinit var shoppingCartRepository : ShoppingCartRepository


    interface onClickAddToCart{
        fun onClick(item: Item)
        fun onImageClick(item: Item)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(cardView: ViewHolder?, position: Int) {
        val item = itemList[position]
        cardView?.tv_ItemTitle?.text = item.title
        cardView?.tv_ItemPrice?.text = item.price.toMoneyString()
        cardView?.tv_seller?.text = item.seller
        cardView?.bt_AddToCart?.setOnClickListener({
            Toast.makeText(cardView.itemView.context, "Item added to cart", Toast.LENGTH_SHORT).show()
            onClickListener.onClick(item)
        })

        Picasso.with(cardView?.itemView?.context?.applicationContext).
                load(item.thumbnailHd).error(R.drawable.ic_not_found).resize(600,600).into(cardView?.iv_ItemPhoto)
        cardView?.iv_ItemPhoto?.setOnClickListener({
            onClickListener.onImageClick(item)
        })

    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.v_item_view,parent,false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tv_ItemTitle by lazy { itemView.findViewById(R.id.tv_item_title) as TextView }
        val tv_ItemPrice by lazy { itemView.findViewById(R.id.tv_item_price) as TextView }
        val tv_seller    by lazy { itemView.findViewById(R.id.tv_seller) as TextView }
        val iv_ItemPhoto by lazy { itemView.findViewById(R.id.iv_item_photo) as ImageView }
        val bt_AddToCart by lazy { itemView.findViewById(R.id.ib_add_to_cart) as FloatingActionButton }
    }
}
