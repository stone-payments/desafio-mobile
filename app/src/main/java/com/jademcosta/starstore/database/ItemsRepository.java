package com.jademcosta.starstore.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jademcosta.starstore.entity.Item;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class ItemsRepository {

    private SQLiteDatabase db;

    public ItemsRepository(Context context) {
        CupboardSQLiteOpenHelper dbHelper = CupboardSQLiteOpenHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
    }

    public void add(Item item) {
        cupboard().withDatabase(db).put(item);
    }
}
