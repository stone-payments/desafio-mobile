package com.stone.mobile.stonestore.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseSqlHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "StoneStore.sql";

    public DataBaseSqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseStoneStore.SQL_CREATE_TABLE_CARRINHO);
        db.execSQL(DataBaseStoneStore.SQL_CREATE_TABLE_TRANSACOES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DataBaseStoneStore.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}