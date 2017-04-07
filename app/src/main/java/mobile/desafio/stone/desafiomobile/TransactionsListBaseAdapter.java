package mobile.desafio.stone.desafiomobile;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import BD.Transaction;


public class TransactionsListBaseAdapter extends BaseAdapter {

    private ArrayList<Transaction> allTransactions;
    private LayoutInflater layoutInflater;

    public TransactionsListBaseAdapter(Context context, ArrayList<Transaction> transactions){
        this.allTransactions = transactions;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return this.allTransactions.size();
    }

    @Override
    public Object getItem(int position) {
        return this.allTransactions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolderTransactions viewHolder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.transactions_details_view, null);
            viewHolder = new ViewHolderTransactions();
            viewHolder.value = (TextView)convertView.findViewById(R.id.transactions_value);
            viewHolder.date = (TextView)convertView.findViewById(R.id.transactions_date);
            viewHolder.hour = (TextView)convertView.findViewById(R.id.transactions_hour);
            viewHolder.cardOwner = (TextView)convertView.findViewById(R.id.transactions_card_owner);
            viewHolder.cardDigits = (TextView)convertView.findViewById(R.id.transactions_card_digits);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder =  (ViewHolderTransactions) convertView.getTag();
        }

        Transaction transaction = this.allTransactions.get(position);
        viewHolder.value.setText("R$ " + transaction.getValue());
        viewHolder.date.setText(transaction.getDate());
        viewHolder.hour.setText(transaction.getHour());
        viewHolder.cardOwner.setText(transaction.getCardOwner());
        viewHolder.cardDigits.setText("xxx-xxxx-xxxx-" + transaction.getCardLastDigits());

        return convertView;
    }

    public void clear(){
        this.allTransactions.clear();
    }

    public void addEntireData(ArrayList<Transaction> transactions){
        this.allTransactions = transactions;
    }

    static class ViewHolderTransactions {
        TextView value;
        TextView date;
        TextView hour;
        TextView cardOwner;
        TextView cardDigits;
    }
}
