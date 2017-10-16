package jb.project.starwarsshoppinglist.cart.adapter

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.realm.Realm
import io.realm.RealmResults
import jb.project.starwarsshoppinglist.R
import jb.project.starwarsshoppinglist.model.Cart


/**
 * Created by joao.neto on 16/10/2017.
 */
class CartAdapter(private val cartList: RealmResults<Cart>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_product_checkout, parent, false)
        return CartAdapter.ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindItems(cartList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bindItems(cart: Cart) {
            val imageProduct = itemView.findViewById<ImageView>(R.id.img_thumbnail) as ImageView
            val textViewTittle = itemView.findViewById<TextView>(R.id.text_tittle) as TextView
            val textPrice = itemView.findViewById<TextView>(R.id.text_price) as TextView
            val btnDelete = itemView.findViewById<ImageButton>(R.id.btn_delete) as ImageButton
            val spinnerQuantity = itemView.findViewById<Spinner>(R.id.spinner_quantity) as Spinner

            spinnerQuantity.adapter = ArrayAdapter<Int>(itemView.context, R.layout.support_simple_spinner_dropdown_item, mutableListOf(0, 1, 2, 3, 4, 5, 6))
            cart.quantity?.let(spinnerQuantity::setSelection)

            Picasso.with(itemView.context).load(cart.thumbnailHd).into(imageProduct, object : Callback {
                override fun onSuccess() {}
                override fun onError() {
                    imageProduct.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.img_not_available))
                }
            })

            textPrice.text = "R$ " + String.format("%.2f", cart.price?.div(100.0))
            textViewTittle.text = cart.title

            btnDelete.setOnClickListener {


            }

        }
    }
}