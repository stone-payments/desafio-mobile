package br.com.tiago.desafiomobile.repository.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.tiago.desafiomobile.model.TransactionModel;
import rx.Observable;

/**
 * Created by TiagoCabral on 8/23/2017.
 */

public class TransactionDao extends SQLiteOpenHelper {

    private static String TAG = TransactionDao.class.getSimpleName();

    public TransactionDao(Context context) {
        super(context, "DesafioStone", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE MyTransaction (id INTEGER PRIMARY KEY, card_holder_name TEXT NOT NULL, card_number TEXT, value INTEGER, date TEXT)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS MyTransaction";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public void insert(TransactionModel transactionModel) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues data = getDataTrasaction(transactionModel);
            db.insert("MyTransaction", null, data);
        } catch (Exception e) {
            Log.e(TAG, "insert: ", e);
        }

    }

    @NonNull
    private ContentValues getDataTrasaction(TransactionModel transactionModel) {
        try {
            ContentValues data = new ContentValues();
            data.put("card_holder_name", transactionModel.getCardHolderName());
            data.put("card_number", transactionModel.getCardNumber());
            data.put("value", transactionModel.getValue());
            data.put("date", transactionModel.getDate());
            return data;
        } catch (Exception e) {
            Log.e(TAG, "getDataTrasaction: ", e);
            return null;
        }
    }

    public Observable<List<TransactionModel>> getTransactions() {

        String sql = "SELECT card_holder_name, card_number, value, date from MyTransaction;";
        SQLiteDatabase db = getReadableDatabase();

        try (Cursor c = db.rawQuery(sql, null)) {
            List<TransactionModel> transactions = new ArrayList<>();
            while (c.moveToNext()) {
                TransactionModel transactionModel = new TransactionModel();
                transactionModel.setCardHolderName(c.getString(c.getColumnIndex("card_holder_name")));
                transactionModel.setCardNumber(c.getString(c.getColumnIndex("card_number")));
                transactionModel.setValue(c.getLong(c.getColumnIndex("value")));
                transactionModel.setDate(c.getString(c.getColumnIndex("date")));
                transactions.add(transactionModel);
            }
            return Observable.just(transactions);

        } catch (Exception e) {
            Log.e(TAG, "getTransactions: ", e);
            return null;
        }
    }

}
