package personal.pedrofigueiredo.milleniumstore.adapters

import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import personal.pedrofigueiredo.milleniumstore.R
import personal.pedrofigueiredo.milleniumstore.data.Product

class ProductListAdapter(private var activity: AppCompatActivity, private var items: ArrayList<Product>) : BaseAdapter() {
    private class ViewHolder(row: View?) {
        var txtProductName: TextView? = null
        var imgProductAvatar: ImageView? = null

        init {
            this.txtProductName = row?.findViewById(R.id.txtProductName)
            this.imgProductAvatar = row?.findViewById(R.id.productImg)
        }
    }

    override fun getView(position: Int, convertView: View?, parentView: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity.layoutInflater as LayoutInflater
            view = inflater.inflate(R.layout.product_list_view, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val product = items[position]
        viewHolder.txtProductName?.text = product.title
        Picasso.with(view?.context).load(product.thumb).into(viewHolder.imgProductAvatar)

        return view as View
    }

    override fun getItem(i: Int): Product {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}