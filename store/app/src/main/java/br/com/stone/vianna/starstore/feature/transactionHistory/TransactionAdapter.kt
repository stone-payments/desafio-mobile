package  br.com.stone.vianna.starstore.feature.transactionHistory

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import  br.com.stone.vianna.starstore.extensions.convertDateToFormat
import  br.com.stone.vianna.starstore.extensions.toMoneyFormat
import  br.com.stone.vianna.starstore.R
import  br.com.stone.vianna.starstore.entity.PaymentTransaction
import kotlinx.android.synthetic.main.view_item_transaction.view.*

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private val transactions: List<PaymentTransaction>

    constructor(transactions: List<PaymentTransaction>) : super() {
        this.transactions = transactions
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.view_item_transaction, parent,
                false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(transactions[position])

    override fun getItemCount() = transactions.size

    open class ViewHolder(private val transactionView: View)
        : RecyclerView.ViewHolder(transactionView) {

        fun bind(transaction: PaymentTransaction) {

            transactionView.tv_transaction_value.text = transaction.value.toMoneyFormat()
            transactionView.tv_transaction_date.text =
                    convertDateToFormat(transaction.transactionDateTime,
                            "yyyy-dd-MM'T'HH:mm:ss.SSS'Z'", "dd/MM/yyyy HH:mm")
            transactionView.tv_card_data.text =
                    "${transaction.cardHolderName} - ${transaction.cardLastDigits}"
        }
    }

}
