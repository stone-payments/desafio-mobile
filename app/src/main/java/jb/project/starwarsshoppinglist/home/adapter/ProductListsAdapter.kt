package jb.project.starwarsshoppinglist.home.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import jb.project.starwarsshoppinglist.R
import jb.project.starwarsshoppinglist.model.Product
import android.content.Intent
import jb.project.starwarsshoppinglist.productDetail.activity.ProductDetailActivity


/**
 * Created by Jb on 13/10/2017.
 */
class ProductListsAdapter(val productList: List<Product>) : RecyclerView.Adapter<ProductListsAdapter.ViewHolder>() {

     override fun getItemCount(): Int {
        return productList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_product_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindItems(productList[position])
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(product: Product) {
            val imageProduct = itemView.findViewById<ImageView>(R.id.product_image) as ImageView
            val textViewTittle = itemView.findViewById<TextView>(R.id.product_tittle) as TextView
            Picasso.with(itemView.context).load(product.thumbnailHd).into(imageProduct, object : Callback {
                override fun onSuccess() {}
                override fun onError() {
                    imageProduct.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.img_not_available))
                }
            })
            textViewTittle.text = product.title

            itemView.setOnClickListener {
                val mIntent = Intent(itemView.context, ProductDetailActivity::class.java).putExtra("product", product)
                itemView.context.startActivity(mIntent)
            }
        }
    }
}