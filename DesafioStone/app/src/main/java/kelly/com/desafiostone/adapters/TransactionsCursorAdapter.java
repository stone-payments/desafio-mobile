package kelly.com.desafiostone.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import kelly.com.desafiostone.R;
import kelly.com.desafiostone.data.TransactionContract;

/**
 * Created by kelly on 30/11/17.
 */

public class TransactionsCursorAdapter extends CursorAdapter {

    public TransactionsCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView tvHolderName = (TextView) view.findViewById(R.id.tv_holder_name);
        TextView tvCardNumber = (TextView) view.findViewById(R.id.tv_card_numer);
        TextView tvTransactionDate = (TextView) view.findViewById(R.id.tv_transation_date);
        TextView tvValue = (TextView) view.findViewById(R.id.tv_value);

        String holderName = cursor.getString(cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_HOLDER_NAME));
        String cardNumber = cursor.getString(cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_LAST_CARD_NUMBERS));
        Double value = cursor.getDouble(cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_VALUE));
        long dateLong = cursor.getLong(cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_DATE));

        Date date = new Date(dateLong);
        DateFormat df = new SimpleDateFormat("MM/yy");
        String stringDate = df.format(date);

        tvHolderName.setText(holderName);
        tvCardNumber.setText(cardNumber);
        tvTransactionDate.setText(stringDate);
        tvValue.setText("R$" + String.format("%.2f", value));
    }
}
