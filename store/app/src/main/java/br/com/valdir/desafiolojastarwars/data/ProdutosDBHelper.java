package br.com.valdir.desafiolojastarwars.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by valdyrtorres on 26/11/2017.
 */

public class ProdutosDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;

    private static final String DATABASE_NAME = "produtoslojastarwars.db";

    public ProdutosDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlTableProdutos = "CREATE TABLE " + ProdutosContract.ProdutoEntry.TABLE_NAME + " (" +
                ProdutosContract.ProdutoEntry._ID + " INTEGER PRIMARY KEY, " +
                ProdutosContract.ProdutoEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                ProdutosContract.ProdutoEntry.COLUMN_PRICE + " REAL, " +
                ProdutosContract.ProdutoEntry.COLUMN_ZIPCODE + " TEXT NOT NULL, " +
                ProdutosContract.ProdutoEntry.COLUMN_SELLER + " TEXT NOT NULL, " +
                ProdutosContract.ProdutoEntry.COLUMN_THUMBNAILHD_PATH + " TEXT NOT NULL, " +
                ProdutosContract.ProdutoEntry.COLUMN_DATA + " TEXT NOT NULL " +
                ");";

        db.execSQL(sqlTableProdutos);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + ProdutosContract.ProdutoEntry.TABLE_NAME);

        onCreate(db);
    }
}
