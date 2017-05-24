package com.stone.lfernandosantos.storewars.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lf.fernandodossantos on 23/05/17.
 */

public class OrderDAO extends SQLiteOpenHelper {

    private static final String NAME = "DBOrder.db3";
    private static final String TABELA = "OrderTable";
    private static final int VERSION = 1;

    public OrderDAO(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = "CREATE TABLE "+ TABELA + "(idOrder TEXT, date TEXT, total TEXT, itens TEXT);";
        db.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String ddl = "DROP TABLE IF EXISTS " + TABELA;
        db.execSQL(ddl);
        onCreate(db);
    }

    //salvando pedido
    public void saveOrder(Order order){
        ContentValues values = new ContentValues();

        values.put("idOrder", String.valueOf(order.idOrder));
        values.put("date", order.date);
        values.put("total", String.valueOf(order.total));
        values.put("itens", order.itens);

        getWritableDatabase().insert(TABELA, null, values);
    }

    //historico de pedidos
    public List<Order> getOrders(){

        List<Order> orders = new ArrayList<>();

        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM "+ TABELA + ";", null );

        while (c.moveToNext()){
            Order order = new Order();

            order.idOrder = c.getLong(c.getColumnIndex("idOrder"));
            order.date = c.getString(c.getColumnIndex("date"));
            order.total = c.getDouble(c.getColumnIndex("total"));
            order.itens = c.getString(c.getColumnIndex("itens"));

            orders.add(order);
        }
        return orders;
    }

}
