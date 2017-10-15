package personal.pedrofigueiredo.milleniumstore.adapters

import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import personal.pedrofigueiredo.milleniumstore.R
import personal.pedrofigueiredo.milleniumstore.data.Order
import java.text.DateFormat

class OrdersListAdapter(private var activity: AppCompatActivity, private var items: ArrayList<Order>) : BaseAdapter() {


    class ViewHolder(row: View?) {
        var txtOrderId: TextView? = null
        var txtCardHolder: TextView? = null
        var txtCardNumber: TextView? = null
        var txtValue: TextView? = null
        var txtDate: TextView? = null

        init {
            txtOrderId = row?.findViewById(R.id.txtOrderId)
            txtCardHolder = row?.findViewById(R.id.txtCardHolder)
            txtCardNumber = row?.findViewById(R.id.txtLastDigits)
            txtValue = row?.findViewById(R.id.txtValue)
            txtDate = row?.findViewById(R.id.txtDateProcessed)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity.layoutInflater as LayoutInflater
            view = inflater.inflate(R.layout.orders_list_view, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val order = items[position]
        val res: Resources = activity.resources

        val fmt = DateFormat.getDateTimeInstance()
        val localDateTime = fmt.format(order.dtProcessed)

        viewHolder.txtOrderId?.text = String.format(res.getString(R.string.txt_order_id), order.orderId)
        viewHolder.txtCardHolder?.text = String.format(res.getString(R.string.txt_order_card_holder), order.cardHolder)
        viewHolder.txtCardNumber?.text = String.format(res.getString(R.string.txt_order_card_last_digits), order.lastFourDigits)
        viewHolder.txtValue?.text = String.format(res.getString(R.string.txt_order_value), order.value.toString())
        viewHolder.txtDate?.text = String.format(res.getString(R.string.txt_order_process_date), localDateTime)


        return view as View
    }

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Order = items[position]

    override fun getItemId(position: Int): Long = position.toLong()
}