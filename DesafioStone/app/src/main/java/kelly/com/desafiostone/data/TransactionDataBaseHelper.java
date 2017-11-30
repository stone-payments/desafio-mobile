package kelly.com.desafiostone.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kelly on 30/11/17.
 */

public class TransactionDataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "transaction.db";
    private static final int DATABASE_VERSION = 1;

    public TransactionDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_TRANSACTION_TABLE =  "CREATE TABLE " + TransactionContract.TransactionEntry.TABLE_NAME + " ("
                + TransactionContract.TransactionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TransactionContract.TransactionEntry.COLUMN_TRANSACTION_HOLDER_NAME + " TEXT NOT NULL, "
                + TransactionContract.TransactionEntry.COLUMN_TRANSACTION_LAST_CARD_NUMBERS + " TEXT NOT NULL, "
                + TransactionContract.TransactionEntry.COLUMN_TRANSACTION_DATE + " INTEGER NOT NULL, "
                + TransactionContract.TransactionEntry.COLUMN_TRANSACTION_VALUE + " REAL NOT NULL);";

        db.execSQL(SQL_CREATE_TRANSACTION_TABLE);
    }

    private static final String SQL_DELETE_TRANSACTION_TABLE =
            "DROP TABLE IF EXISTS " + TransactionContract.TransactionEntry.TABLE_NAME;

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL(SQL_DELETE_TRANSACTION_TABLE);
            onCreate(db);
        }
    }
}
