package BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDCore extends SQLiteOpenHelper {

    private static final String BD_NAME = "transactionsBD";
    private static final int BD_VERSION = 3;


    public BDCore(Context ctx){
        super(ctx, BD_NAME, null, BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("create table transactions(_id integer primary key autoincrement, " +
                "value integer not null, date text not null, hour text not null," +
                "card_last_digits text not null, card_owner text not null );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int arg1, int arg2) {
        bd.execSQL("drop table transactions;");
        onCreate(bd);
    }
}
