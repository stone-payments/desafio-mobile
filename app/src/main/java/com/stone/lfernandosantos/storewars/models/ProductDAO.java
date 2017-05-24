package com.stone.lfernandosantos.storewars.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lf.fernandodossantos on 20/05/17.
 */

public class ProductDAO extends SQLiteOpenHelper {

    private static final String NAME = "DBProduct.db3";
    private static final String TABELA = "Product";
    private static final int VERSAO = 1;


    public ProductDAO(Context context) {
        super(context, NAME, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String ddl = "CREATE TABLE "+ TABELA + "(id INTEGER PRIMARY KEY, img TEXT, title TEXT, seller TEXT, price TEXT, zipcode TEXT, data TEXT" +
                ", carrinho  TEXT, compra TEXT);";
        db.execSQL(ddl);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String ddl = "DROP TABLE IF EXISTS "+ TABELA;
        db.execSQL(ddl);
        onCreate(db);

    }

    //salvar produto
    public void saveProduct(Product product){

        ContentValues values = new ContentValues();

        values.put("img", product.thumbnailHd);
        values.put("title", product.title);
        values.put("seller", product.seller);
        values.put("price", String.valueOf(product.price));
        values.put("zipcode", product.zipcode);
        values.put("data", product.date);
        values.put("carrinho", product.carrinho);
        values.put("compra", product.compra);

        getWritableDatabase().insert(TABELA, null, values);

    }

    //hitórico de produtos comprados
    public List<Product> getProductsCompras(){

        List<Product> products = new ArrayList<>();

        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM "+ TABELA + ";", null );

        while (c.moveToNext()){
            Product product = new Product();

            product.id = c.getLong(c.getColumnIndex("id"));
            product.thumbnailHd = c.getString(c.getColumnIndex("img"));
            product.title = c.getString(c.getColumnIndex("title"));
            product.seller = c.getString(c.getColumnIndex("seller"));
            product.price = Double.valueOf(c.getString(c.getColumnIndex("price")));
            product.zipcode = c.getString(c.getColumnIndex("zipcode"));
            product.date = c.getString(c.getColumnIndex("data"));
            product.carrinho = c.getString(c.getColumnIndex("carrinho"));
            product.compra = c.getString(c.getColumnIndex("compra"));

            products.add(product);
        }
        return products;
    }

    // produtos salvos no carrinho
    public List<Product> getProductsCarrinho(){

        List<Product> products = new ArrayList<>();

        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM "+ TABELA + ";", null );

        while (c.moveToNext()){
            Product product = new Product();

            product.id = c.getLong(c.getColumnIndex("id"));
            product.thumbnailHd = c.getString(c.getColumnIndex("img"));
            product.title = c.getString(c.getColumnIndex("title"));
            product.seller = c.getString(c.getColumnIndex("seller"));
            product.price = Double.valueOf(c.getString(c.getColumnIndex("price")));
            product.zipcode = c.getString(c.getColumnIndex("zipcode"));
            product.date = c.getString(c.getColumnIndex("data"));
            product.carrinho = c.getString(c.getColumnIndex("carrinho"));
            product.compra = c.getString(c.getColumnIndex("compra"));

            if (product.carrinho.equals("1")){
                products.add(product);
            }

        }
        return products;
    }

    public void deletar(Product product) {

        String [] args = {product.id.toString()};
        getWritableDatabase().delete(TABELA,"id=?",args);

        Log.i("DELET", "" + args);
    }
}
