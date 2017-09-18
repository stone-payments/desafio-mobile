package victorcruz.dms.transaction;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TransactionsHandler {

    private ListView transactionsListView;

    private ArrayList<Transaction> transactionsArrayList;
    private TransactionAdapter transactionAdapter;
    private SQLiteDatabase sqLiteDatabase;

    public TransactionsHandler(Activity act, ListView transactionsListView){
        this.transactionsListView = transactionsListView;

        transactionsArrayList = new ArrayList<>();

        sqLiteDatabase = act.openOrCreateDatabase("Transactions", Context.MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS transactions (value INTEGER(7), " +
                                    "name VARCHAR, cardNumber VARCHAR, date VARCHAR)");

        refreshTransactionsArrayList();

        transactionAdapter = new TransactionAdapter(act, transactionsArrayList);
        refreshTransactionsView();
    }

    public void newTransaction(JSONObject jsonObject){

        try {
            int value = jsonObject.getInt("value");

            String name = jsonObject.getString("card_holder_name");
            name = "\"" + name + "\"";

            String cardNumber = jsonObject.getString("card_number");
            if (cardNumber.length() > 4) cardNumber = cardNumber.substring(cardNumber.length() - 4);
            cardNumber = "\"" + cardNumber + "\"";

            Date todayDate = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("kk:mm dd/MM/yyyy");
            String date = formatter.format(todayDate);
            date = "\"" + date + "\"";

            sqLiteDatabase.execSQL("INSERT INTO Transactions (value, name, cardNumber, date) " +
                    "VALUES (" + value + "," + name + "," + cardNumber + "," + date + ")");

            System.out.println(value + " " + name + " " + cardNumber + " " + date);

        } catch (Exception e) {
            e.printStackTrace();
        }

        refreshTransactionsArrayList();
        refreshTransactionsView();

    }

    public void refreshTransactionsArrayList(){

        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM Transactions", null);
        c.moveToFirst();

        if (transactionsArrayList.size() < c.getCount()){
            int sizeDiference = c.getCount() - transactionsArrayList.size();
            for (int i = 0; i < c.getCount() - sizeDiference; i++){
                c.moveToNext();
            }

            for (int j = transactionsArrayList.size(); j < c.getCount(); j++ ) {

                transactionsArrayList.add(new Transaction(c.getInt(c.getColumnIndex("value")),
                        c.getString(c.getColumnIndex("name")), c.getString(c.getColumnIndex("cardNumber")),
                        c.getString(c.getColumnIndex("date"))));
                if (!(j + 1 == c.getCount())){
                    c.moveToNext();
                }

            }
        }
    }

    public void refreshTransactionsView(){
        transactionsListView.setAdapter(transactionAdapter);
    }


}
