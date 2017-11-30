package kelly.com.desafiostone.activities;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import kelly.com.desafiostone.R;
import kelly.com.desafiostone.models.FullTransaction;
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
                    sendTransation();
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

                    return true;

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
        fullTransaction.setCvv(Integer.parseInt(cvvString));
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

    public class SendTransactionAsyncTask extends AsyncTask<FullTransaction, String, String> {

        String result;

        @Override
        protected String doInBackground(FullTransaction... params) {
            FullTransaction fullTransaction = params[0];

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
        }
    }

}
