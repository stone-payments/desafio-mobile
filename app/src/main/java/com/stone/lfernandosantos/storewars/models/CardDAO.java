package com.stone.lfernandosantos.storewars.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lf.fernandodossantos on 21/05/17.
 */

public class CardDAO extends SQLiteOpenHelper {

    private static final String NAME = "DBCard.db3";
    private static final String TABELA = "Card";
    private static final int VERSAO = 1;

    public CardDAO(Context context) {
        super(context, NAME, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = "CREATE TABLE "+ TABELA + "(bandeira TEXT, card TEXT, nome TEXT, validade TEXT, cvv TEXT);";
        db.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String ddl = "DROP TABLE IF EXISTS " + TABELA;
        db.execSQL(ddl);
        onCreate(db);
    }

    //salvando dados do cartao
    public void saveCard(Card card){
        ContentValues values = new ContentValues();

        values.put("bandeira", card.bandeira);
        values.put("card", card.numCard);
        values.put("nome", card.nome);
        values.put("validade", card.validade);
        values.put("cvv", card.cvv);

        getWritableDatabase().insert(TABELA, null, values);
    }

    //cartoes ja cadastrados
    public List<Card> getCards(){

        List<Card> cards = new ArrayList<>();

        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM "+ TABELA + ";", null );

        while (c.moveToNext()){
            Card card = new Card();

            card.bandeira = c.getString(c.getColumnIndex("card"));
            card.numCard = c.getString(c.getColumnIndex("card"));
            card.nome = c.getString(c.getColumnIndex("nome"));
            card.validade = c.getString(c.getColumnIndex("validade"));
            card.cvv = c.getString(c.getColumnIndex("cvv"));

            cards.add(card);
        }
        return cards;
    }

}
