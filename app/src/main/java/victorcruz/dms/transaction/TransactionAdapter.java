package victorcruz.dms.transaction;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import victorcruz.dms.R;

public class TransactionAdapter extends BaseAdapter{

    private Activity act;
    private ArrayList<Transaction> transactionsArrayList;

    public TransactionAdapter(Activity act, ArrayList transactionsArrayList){
        this.act = act;
        this.transactionsArrayList = transactionsArrayList;
    }

    @Override
    public int getCount() {
        return transactionsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return transactionsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.transaction, parent, false);

        Transaction transaction = transactionsArrayList.get(position);

        TextView value = (TextView) view.findViewById(R.id.transactionValueTextView);
        TextView name = (TextView) view.findViewById(R.id.transactionNameTextView);
        TextView cardNumber = (TextView) view.findViewById(R.id.transactionCardTextView);
        TextView date = (TextView) view.findViewById(R.id.transactionDateTextView);
        TextView hour = (TextView) view.findViewById(R.id.transactionHourTextView);

        DecimalFormat decimalFormat = new DecimalFormat("#,#####,00");
        String _value = decimalFormat.format((double) transaction.getValue());
        _value = "R$ " + _value;
        value.setText(_value);

        name.setText(transaction.getCardName());

        cardNumber.setText("XXXX XXXX XXXX " + transaction.getCardNumber());

        hour.setText(transaction.getDate().substring(0,5));
        date.setText(transaction.getDate().substring(6,16));

        return view;
    }
}
