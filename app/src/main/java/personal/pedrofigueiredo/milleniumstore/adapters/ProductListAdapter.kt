package personal.pedrofigueiredo.milleniumstore.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import personal.pedrofigueiredo.milleniumstore.R
import personal.pedrofigueiredo.milleniumstore.data.Product

class ProductListAdapter(private var activity: Activity, private var items: ArrayList<Product>) : BaseAdapter() {
    private class ViewHolder(row: View?) {
        var txtProductName: TextView? = null

        init {
            this.txtProductName = row?.findViewById<TextView>(R.id.txtProductName)
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

        var product = items[position]
        viewHolder.txtProductName?.text = product.title

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