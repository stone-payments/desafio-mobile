package com.jademcosta.starstore.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jademcosta.starstore.entity.Item;

import java.util.ArrayList;
import java.util.List;

import nl.qbusict.cupboard.QueryResultIterable;

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

    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();

        QueryResultIterable<Item> itr = cupboard().withDatabase(db).query(Item.class).query();

        for(Item item : itr) {
            items.add(item);
        }

        itr.close();
        return items;
    }
}
