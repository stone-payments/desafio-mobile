package com.example.leonardo.desafiomobile.auxiliares;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.leonardo.desafiomobile.Objetos.Database;

/**
 * Created by Leonardo on 21/04/2017.
 */


public class DbHandler extends SQLiteOpenHelper{

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "Finalmente.db";
    private static final String TABLE_TRANSAÇÕES = "transações";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_VALOR = "Valor";
    private static final String COLUMN_DEH = "Data";
    private static final String COLUMN_ULTIMOSDIGITOS = "ultimosDigitos";
    private static final String COLUMN_NOME = "Nome";
    private static final String TAG = "DbHelper";

    public DbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " +
                TABLE_TRANSAÇÕES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_VALOR + " DOUBLE," + COLUMN_DEH + " TEXT,"
                + COLUMN_ULTIMOSDIGITOS + " INTEGER," +COLUMN_NOME + " TEXT" + ")";

        db.execSQL(CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSAÇÕES);
        onCreate(db);

    }

    public void addData(Database database){

        ContentValues values = new ContentValues();
        values.put(COLUMN_VALOR,database.getValor());
        values.put(COLUMN_DEH,database.getDeh());
        values.put(COLUMN_ULTIMOSDIGITOS,database.getUltimosDig());
        values.put(COLUMN_NOME,database.getNome());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_TRANSAÇÕES,null, values);
        db.close();

    }
    public String getTableAsString(SQLiteDatabase db) {
        Log.d(TAG, "getTableAsString called");
        String tableString = String.format("Table %s:\n", TABLE_TRANSAÇÕES);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + TABLE_TRANSAÇÕES, null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name: columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }
        allRows.close();

        return tableString;
    }
}