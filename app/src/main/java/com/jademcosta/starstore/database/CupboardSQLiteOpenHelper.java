package com.jademcosta.starstore.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jademcosta.starstore.entity.Item;
import com.jademcosta.starstore.entity.Transaction;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class CupboardSQLiteOpenHelper extends SQLiteOpenHelper {

    private static CupboardSQLiteOpenHelper instance;

    private static final String DATABASE_NAME = "cart.db";
    private static final int DATABASE_VERSION = 1;

    static {
        cupboard().register(Item.class);
        cupboard().register(Transaction.class);
    }

    private CupboardSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static CupboardSQLiteOpenHelper getInstance(Context context) {
        if(instance == null) {
            instance = new CupboardSQLiteOpenHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        cupboard().withDatabase(db).createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        cupboard().withDatabase(db).upgradeTables();
    }
}
