package mobile.desafio.stone.desafiomobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import BD.BD;
import BD.Transaction;
public class TransactionsActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_transactions);

        BD bd = new BD(this);
        List<Transaction> transactions = bd.getAllTransactions();
        for(Transaction t : transactions){
            Log.d("Transações", t.getDate() + "   " + t.getCardLastDigits() + "   " + t.getHour());
        }

        listView = (ListView)findViewById(R.id.listView_transactions);
        TransactionsListBaseAdapter adapter = new TransactionsListBaseAdapter(this, (ArrayList)transactions);
        listView.setAdapter(adapter);
    }


}
