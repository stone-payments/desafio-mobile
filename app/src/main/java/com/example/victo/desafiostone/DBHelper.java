package com.example.victo.desafiostone;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victo on 27/10/2017.
 */

public class DBHelper {

    private static final String DATABASE_NAME = "bancodedados.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "produto";


    private Context context;
    private SQLiteDatabase db;

    private SQLiteStatement insertStnt;
    private static final String INSERT = "INSERT INTO " + TABLE_NAME + " (price, nome, cartao) values (?,?,?)";

    public DBHelper(Context context) {
        this.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
        this.insertStnt = this.db.compileStatement(INSERT);
    }

    public long insert(String price, String nome, String cartao) {

        this.insertStnt.bindString(1, price);
        this.insertStnt.bindString(2, nome);
        this.insertStnt.bindString(3, cartao);
        return this.insertStnt.executeInsert();
    }

    public void deleteAll() {
        this.db.delete(TABLE_NAME, null, null);
    }

    public List<ItensBanco> queryGetAll() {

        List<ItensBanco> list = new ArrayList<ItensBanco>();

        try {
            Cursor cursor = this.db.query(TABLE_NAME, new String[]{"price", "nome", "cartao"},
                    null, null, null, null, null, null);

            int nregistros = cursor.getCount();

            if (nregistros != 0) {
                cursor.moveToFirst();
                do {
                    if (cursor != null) {
                        String price = cursor.getString(0);
                        String nome = cursor.getString(1);
                        String cartao = cursor.getString(2);

                        ItensBanco itens = new ItensBanco();
                        itens.setPrice(price);
                        itens.setNome(nome);
                        itens.setCartao(cartao);

                        list.add(itens);
                    }
                } while (cursor.moveToNext());

                if (cursor != null && !cursor.isClosed())
                    cursor.close();
                return list;

            } else
                return null;
        } catch (Exception err) {
            return null;
        }
    }

    private static class OpenHelper extends SQLiteOpenHelper {
        OpenHelper(Context context){
            super (context, DATABASE_NAME , null , DATABASE_VERSION);
         }
     public void onCreate (SQLiteDatabase db){
         String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +" (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT," +
                 "price TEXT, cartao TEXT);";

         db.execSQL(sql);
     }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
            onCreate(db);
        }

    }
}
