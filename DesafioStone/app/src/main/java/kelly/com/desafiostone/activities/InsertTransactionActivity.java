package kelly.com.desafiostone.activities;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import kelly.com.desafiostone.R;
import kelly.com.desafiostone.data.TransactionContract;
import kelly.com.desafiostone.models.FullTransaction;
import kelly.com.desafiostone.models.Item;
import kelly.com.desafiostone.models.ShortTransaction;
import kelly.com.desafiostone.network.QueryUtils;

public class InsertTransactionActivity extends AppCompatActivity {

    private EditText mEditTextHolderName;
    private EditText mEditTextCardNumber;
    private EditText mEditTextExpirationDate;
    private EditText mEditTextCvv;

    private Button mButtonFinish;
    private TextView mTextViewTotal;

    private double total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_transaction);
        bindUi();

        Bundle bundle = getIntent().getExtras();
        total = bundle.getDouble("total");

        mTextViewTotal.setText("R$" + String.format("%.2f", total));

        mButtonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidForm() && isInternetConnectionAvailable()){
                    showConfirmTransationDialog();
                }
            }
        });

    }

    private void bindUi(){
        mEditTextHolderName = (EditText) findViewById(R.id.edt_holder_name);
        mEditTextCardNumber = (EditText) findViewById(R.id.edt_card_numer);
        mEditTextExpirationDate = (EditText) findViewById(R.id.edt_expiration_date);
        mEditTextCvv = (EditText) findViewById(R.id.edt_cvv);

        mButtonFinish = (Button) findViewById(R.id.btn_finish);
        mTextViewTotal = (TextView) findViewById(R.id.tv_total);

    }

    private boolean isValidForm(){

        String holderName = mEditTextHolderName.getText().toString();
        String cardNumber = mEditTextCardNumber.getText().toString();
        String dateString = mEditTextExpirationDate.getText().toString();
        String cvvString = mEditTextCvv.getText().toString();

        if (!holderName.isEmpty() &&
                !cardNumber.isEmpty() &&
                !dateString.isEmpty() &&
                !cvvString.isEmpty()){

            if (cardNumber.length() <= 16){

                if (isValidDate(dateString)){

                    if (cvvString.length() == 3){

                        return true;

                    } else {
                        Toast.makeText(getBaseContext(), R.string.message_invalid_cvv, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getBaseContext(), R.string.message_invalid_date_format, Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getBaseContext(), R.string.message_invalid_card_number, Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getBaseContext(), R.string.message_form_incomplete, Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    private boolean isValidDate(String dateString){
        if (dateString.length() == 5 &&
                dateString.charAt(2) == '/' &&
                Integer.parseInt(dateString.substring(0,2)) <= 12){

            return true;
        }
        return false;
    }

    private boolean isInternetConnectionAvailable(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if ((netInfo != null) && (netInfo.isConnectedOrConnecting()) && (netInfo.isAvailable()))
            return true;
        else
            return false;
    }

    private void sendTransation(){
        String holderName = mEditTextHolderName.getText().toString();
        String cardNumber = mEditTextCardNumber.getText().toString();
        String dateString = mEditTextExpirationDate.getText().toString();
        String cvvString = mEditTextCvv.getText().toString();

        FullTransaction fullTransaction = new FullTransaction();
        fullTransaction.setHolderName(holderName);
        fullTransaction.setCardNumber(cardNumber);
        fullTransaction.setExpirationDate(stringToDate(dateString, "MM/yy"));
        fullTransaction.setCvv(cvvString);
        fullTransaction.setValue(total);

        new SendTransactionAsyncTask().execute(fullTransaction);
    }

    private Date stringToDate(String aDate,String aFormat) {

        if(aDate==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;

    }

    private void showConfirmTransationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.message_finish_transation);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                sendTransation();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showTransationNegativeResultDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.message_problem_transation);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showTransationPositiveResultDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.message_successful_transaction);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveTransaction(ShortTransaction shortTransaction) {

        ContentValues values = new ContentValues();
        values.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_HOLDER_NAME, shortTransaction.getHolderName());
        values.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_LAST_CARD_NUMBERS, shortTransaction.getLastCardNumbers());
        values.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_VALUE, shortTransaction.getValue());
        values.put(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_DATE, shortTransaction.getLongOcurrenceDate());

        getContentResolver().insert(TransactionContract.TransactionEntry.CONTENT_URI, values);

    }

    private ShortTransaction fullToShortTransation(FullTransaction fullTransaction){

        String fullCredCardNumber = fullTransaction.getCardNumber();

        ShortTransaction result = new ShortTransaction();
        result.setHolderName(fullTransaction.getHolderName());
        result.setLastCardNumbers(fullCredCardNumber.substring(fullCredCardNumber.length()-4,
                fullCredCardNumber.length()));
        result.setOccurrenceDate(new Date(System.currentTimeMillis()));
        result.setValue(fullTransaction.getValue());

        return result;
    }

    public class SendTransactionAsyncTask extends AsyncTask<FullTransaction, String, String> {

        String result;
        FullTransaction fullTransaction;

        @Override
        protected String doInBackground(FullTransaction... params) {
            fullTransaction = params[0];

            String urlString = getString(R.string.post_transactions_url);

            try {
                result = QueryUtils.sendTransaction(urlString, fullTransaction);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s.contains("Sucess")){
                saveTransaction(fullToShortTransation(fullTransaction));
                showTransationPositiveResultDialog();
            } else {
                showTransationNegativeResultDialog();
            }
        }
    }
}
