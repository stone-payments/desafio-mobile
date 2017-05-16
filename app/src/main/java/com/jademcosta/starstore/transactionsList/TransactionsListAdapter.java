package com.jademcosta.starstore.transactionsList;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jademcosta.starstore.R;
import com.jademcosta.starstore.entity.Transaction;

import java.util.List;

public class TransactionsListAdapter extends
        RecyclerView.Adapter<TransactionsListAdapter.TransactionsViewHolder> {

    private List<Transaction> transactions;
    private Context context;

    public TransactionsListAdapter(List<Transaction> transactions, Context context) {
        this.transactions = transactions;
        this.context = context;
    }

    @Override
    public TransactionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transactions_list_row,
                parent, false);
        return new TransactionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransactionsViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);

        holder.creditCardNumber.setText(String.format(
                context.getString(R.string.transactions_list_obfuscated_card_number),
                transaction.getCreditCardLastFourDigits()));

        holder.value.setText(String.format(context.getString(R.string.transactions_list_price),
                transaction.getValue()));

        holder.cardOwnerName.setText(transaction.getName());
        holder.dateTime.setText(transaction.getDateTime());
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    class TransactionsViewHolder extends RecyclerView.ViewHolder {

        TextView dateTime;
        TextView cardOwnerName;
        TextView value;
        TextView creditCardNumber;

        public TransactionsViewHolder(View transactionView) {
            super(transactionView);

            dateTime = (TextView) transactionView.findViewById(R.id.transactions_list_datetime);
            cardOwnerName =
                    (TextView) transactionView.findViewById(R.id.transactions_list_owner_name);
            value = (TextView) transactionView.findViewById(R.id.transactions_list_value);
            creditCardNumber =
                    (TextView) transactionView.findViewById(R.id.transactions_list_card_number);
        }
    }
}
