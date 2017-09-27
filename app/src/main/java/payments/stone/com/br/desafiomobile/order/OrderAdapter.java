package payments.stone.com.br.desafiomobile.order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import payments.stone.com.br.desafiomobile.R;
import payments.stone.com.br.desafiomobile.commons.Navigation;
import payments.stone.com.br.desafiomobile.commons.Utils;
import payments.stone.com.br.desafiomobile.model.Order;

/**
 * Created by william.gouvea on 9/25/17.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderItemViewHolder> {
    private Context mContext;
    private List<Order> items;
    private Navigation mNavigation;

    public OrderAdapter(Context mContext, List<Order> items, Navigation mNavigation) {
        this.mContext = mContext;
        this.items = items;
        this.mNavigation = mNavigation;
    }

    @Override
    public OrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);

        return new OrderAdapter.OrderItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderItemViewHolder holder, int position) {
        Order item = items.get(position);
        holder.cardHolder.setText(item.getCardHolder());
        holder.cardNumber.setText(item.getCardNumber());

        holder.transactionDate.setText(Utils.dateFrom(item.getTransactionDate()));
        holder.value.setText(Utils.getPriceFormatted(Long.parseLong(item.getValue())));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class OrderItemViewHolder extends RecyclerView.ViewHolder {
        public TextView cardHolder, cardNumber, transactionDate, value;
        public View mRoot;

        public OrderItemViewHolder(View itemView) {
            super(itemView);
            mRoot = itemView;
            cardHolder = (TextView) mRoot.findViewById(R.id.item_card_holder);
            cardNumber = (TextView) mRoot.findViewById(R.id.item_card_number);
            transactionDate = (TextView) mRoot.findViewById(R.id.item_transaction_date);
            value = (TextView) mRoot.findViewById(R.id.item_value);

        }
    }
}
