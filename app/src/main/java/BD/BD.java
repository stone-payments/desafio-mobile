package BD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

public class BD {
    private SQLiteDatabase bd;

    public BD(Context context){
        BDCore auxBd = new BDCore(context);
        bd = auxBd.getWritableDatabase();
    }


    public void insert(Transaction transaction){
        ContentValues values = new ContentValues();
        values.put("value", transaction.getValue());
        values.put("date", transaction.getDate());
        values.put("hour", transaction.getHour());
        values.put("card_last_digits", transaction.getCardLastDigits());
        values.put("card_owner", transaction.getCardOwner());

        bd.insert("transactions", null, values);
    }

    public List<Transaction> getAllTransactions(){
        List<Transaction> list = new ArrayList<Transaction>();
        String[] columns = new String[]{"_id", "value", "date", "hour", "card_last_digits", "card_owner"};

        Cursor cursor = bd.query("transactions", columns, null, null, null, null, "_id ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                Transaction t = new Transaction();
                t.setId(cursor.getInt(0));
                t.setValue(cursor.getInt(1));
                t.setDate(cursor.getString(2));
                t.setHour(cursor.getString(3));
                t.setCardLastDigits(cursor.getString(4));
                t.setCardOwner(cursor.getString(5));
                list.add(t);

            }while(cursor.moveToNext());
        }

        return(list);
    }

}
