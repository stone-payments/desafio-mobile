package com.jademcosta.starstore.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jademcosta.starstore.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class TransactionsRepository {

    private SQLiteDatabase db;

    public TransactionsRepository(Context context) {
        CupboardSQLiteOpenHelper dbHelper = CupboardSQLiteOpenHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
    }

    public void add(Transaction transaction) {
        cupboard().withDatabase(db).put(transaction);
    }

    public List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        QueryResultIterable<Transaction> itr = cupboard().withDatabase(db).query(Transaction.class).query();

        for(Transaction transaction: itr) {
            transactions.add(transaction);
        }

        itr.close();
        return transactions;
    }
}
