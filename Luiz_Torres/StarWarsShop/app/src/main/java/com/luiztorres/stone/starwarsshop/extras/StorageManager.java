package com.luiztorres.stone.starwarsshop.extras;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class StorageManager extends SQLiteOpenHelper
{
    private static StorageManager mInstance;
    private static Context mCtx;

    private static final String DATABASE_NAME = "starWarsShop";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ORDERS = "orders";
    private static final String KEY_ORDER_ID = "id";
    private static final String KEY_ORDER_VALUE = "value";
    private static final String KEY_ORDER_DATE = "date";
    private static final String KEY_ORDER_LASTNUMBERS = "lastnumbers";
    private static final String KEY_ORDER_NAME = "name";

    private StorageManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mCtx = context;
    }

    public static synchronized StorageManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new StorageManager(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS +
                "(" +
                KEY_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_ORDER_VALUE + " MONEY," +
                KEY_ORDER_DATE + " SMALLDATETIME," +
                KEY_ORDER_LASTNUMBERS + " VARCHAR(4)," +
                KEY_ORDER_NAME + " VARCHAR(255)" +
                ")";

        db.execSQL(CREATE_ORDERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addOrder(Order order) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_ORDER_VALUE, order.getValue()+"");
            values.put(KEY_ORDER_DATE, order.getDate()+"");
            values.put(KEY_ORDER_LASTNUMBERS, order.getLastNubmers()+"");
            values.put(KEY_ORDER_NAME, order.getName()+"");

            db.insertOrThrow(TABLE_ORDERS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add order to database");
        } finally {
            db.endTransaction();
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();

        String ORDERS_SELECT_QUERY =
                String.format("SELECT * FROM %s",
                        TABLE_ORDERS);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(ORDERS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Order order = new Order();
                    order.setValue(cursor.getDouble(cursor.getColumnIndex(KEY_ORDER_VALUE)));
                    order.setDate(cursor.getString(cursor.getColumnIndex(KEY_ORDER_DATE)));
                    order.setLastNubmers(cursor.getString(cursor.getColumnIndex(KEY_ORDER_LASTNUMBERS)));
                    order.setName(cursor.getString(cursor.getColumnIndex(KEY_ORDER_NAME)));
                    orders.add(order);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get orders from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return orders;
    }
}
