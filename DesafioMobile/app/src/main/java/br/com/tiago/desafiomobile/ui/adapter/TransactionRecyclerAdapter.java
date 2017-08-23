package br.com.tiago.desafiomobile.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.tiago.desafiomobile.R;
import br.com.tiago.desafiomobile.model.TransactionModel;
import br.com.tiago.desafiomobile.utils.FormatItemValue;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TiagoCabral on 8/22/2017.
 */

public class TransactionRecyclerAdapter extends RecyclerView.Adapter<TransactionRecyclerAdapter.TransactionViewHolder> {

    private List<TransactionModel> transactionList;

    public TransactionRecyclerAdapter() {
        this.transactionList = new ArrayList<>();
    }

    @Override
    public TransactionRecyclerAdapter.TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new TransactionRecyclerAdapter.TransactionViewHolder(v);
    }


    @Override
    public void onBindViewHolder(TransactionRecyclerAdapter.TransactionViewHolder holder, int position) {
        holder.cardHolderName.setText(transactionList.get(position).getCardHolderName());
        holder.cardNumber.setText(transactionList.get(position).getCardNumber());
        holder.value.setText(FormatItemValue.priceFormat(transactionList.get(position).getValueDouble()));
        holder.date.setText(transactionList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public void addAll(List<TransactionModel> transactionModels) {
        if (transactionModels != null && transactionModels.size() > 0) {
            this.transactionList.clear();
            this.transactionList.addAll(transactionModels);
            notifyDataSetChanged();
        }
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.transaction_card_holder_name)
        TextView cardHolderName;
        @BindView(R.id.transaction_card_number)
        TextView cardNumber;
        @BindView(R.id.transaction_value)
        TextView value;
        @BindView(R.id.transaction_date)
        TextView date;

        public TransactionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
