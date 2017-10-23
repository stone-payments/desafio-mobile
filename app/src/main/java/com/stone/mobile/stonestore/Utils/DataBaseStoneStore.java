package com.stone.mobile.stonestore.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.stone.mobile.stonestore.Utils.DataBaseStoneStore.Store.CARRINHO_CAMPO_FOTO_PRODUTO;
import static com.stone.mobile.stonestore.Utils.DataBaseStoneStore.Store.CARRINHO_CAMPO_ID;
import static com.stone.mobile.stonestore.Utils.DataBaseStoneStore.Store.CARRINHO_CAMPO_NOME_PRODUTO;
import static com.stone.mobile.stonestore.Utils.DataBaseStoneStore.Store.CARRINHO_CAMPO_PRECO_PRODUTO;
import static com.stone.mobile.stonestore.Utils.DataBaseStoneStore.Store.CARRINHO_CAMPO_VENDEDOR_PRODUTO;
import static com.stone.mobile.stonestore.Utils.DataBaseStoneStore.Store.TABELA_CARRINHO;
import static com.stone.mobile.stonestore.Utils.DataBaseStoneStore.Store.TABELA_TRANSACOES;
import static com.stone.mobile.stonestore.Utils.DataBaseStoneStore.Store.TRANSACOES_CAMPO_CARTAO;
import static com.stone.mobile.stonestore.Utils.DataBaseStoneStore.Store.TRANSACOES_CAMPO_CARTAO_NOME;
import static com.stone.mobile.stonestore.Utils.DataBaseStoneStore.Store.TRANSACOES_CAMPO_DATA;
import static com.stone.mobile.stonestore.Utils.DataBaseStoneStore.Store.TRANSACOES_CAMPO_ID;
import static com.stone.mobile.stonestore.Utils.DataBaseStoneStore.Store.TRANSACOES_CAMPO_VALOR;

public class DataBaseStoneStore {



    public static final String TEXT_TYPE = " TEXT ";
    public static final String TEXT_NOT_NULL_TYPE = " TEXT NOT NULL ";
    public static final String REAL_TYPE = " REAL ";
    public static final String INTEGER_TYPE = " INTEGER ";
    public static final String COMMA_SEP = ",";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABELA_CARRINHO + ", " + TABELA_TRANSACOES + ";";

    public static final String SQL_CREATE_TABLE_CARRINHO = "CREATE TABLE "
            + TABELA_CARRINHO + " (" + CARRINHO_CAMPO_ID + INTEGER_TYPE + " AUTO_INCREMENT " + COMMA_SEP
            + CARRINHO_CAMPO_NOME_PRODUTO + TEXT_NOT_NULL_TYPE + COMMA_SEP
            + CARRINHO_CAMPO_PRECO_PRODUTO + INTEGER_TYPE + COMMA_SEP
            + CARRINHO_CAMPO_VENDEDOR_PRODUTO + TEXT_NOT_NULL_TYPE + COMMA_SEP
            + CARRINHO_CAMPO_FOTO_PRODUTO + TEXT_TYPE
            + ");";

    public static final String SQL_CREATE_TABLE_TRANSACOES = "CREATE TABLE "
            + TABELA_TRANSACOES + " (" + TRANSACOES_CAMPO_ID + INTEGER_TYPE + " AUTO_INCREMENT " + COMMA_SEP
            + TRANSACOES_CAMPO_VALOR + INTEGER_TYPE + COMMA_SEP
            + TRANSACOES_CAMPO_DATA + TEXT_TYPE + COMMA_SEP
            + TRANSACOES_CAMPO_CARTAO + TEXT_NOT_NULL_TYPE + COMMA_SEP
            + TRANSACOES_CAMPO_CARTAO_NOME + TEXT_NOT_NULL_TYPE
            + ");";

    private DataBaseSqlHelper dataBaseSqlHelper;

    public DataBaseStoneStore(Context context) {
        dataBaseSqlHelper = new DataBaseSqlHelper(context);
    }

    public long inserirItemCarrinho(Produto produto) {

        SQLiteDatabase db = dataBaseSqlHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CARRINHO_CAMPO_NOME_PRODUTO, produto.getTitle());
        values.put(CARRINHO_CAMPO_PRECO_PRODUTO, produto.getPrice());
        values.put(CARRINHO_CAMPO_VENDEDOR_PRODUTO, produto.getSeller());
        values.put(CARRINHO_CAMPO_FOTO_PRODUTO, produto.getThumbnailHd());

        long newRowId;
        newRowId = db.insert(
                TABELA_CARRINHO,
                "",
                values);

        Log.d("DEBUG","Adicionou:"+ newRowId);
        db.close();
        return newRowId;
    }

    public int excluirItemCarrinho(Produto produto) {

        SQLiteDatabase db = dataBaseSqlHelper.getWritableDatabase();

        String selection = CARRINHO_CAMPO_ID + " LIKE ?";

        String[] selectionArgs = { String.valueOf(produto.getId()) };

        int linhasAafetadas = db.delete(TABELA_CARRINHO, selection, selectionArgs);

        Log.d("DEBUG","Linhas:"+ linhasAafetadas);

        db.close();

        return linhasAafetadas;
    }

    public List<Produto> buscarProdutosCarrinho() {

        List<Produto> produtoList = new ArrayList<>();

        SQLiteDatabase db = dataBaseSqlHelper.getReadableDatabase();

        String sql = "SELECT * FROM " + TABELA_CARRINHO;

        String[] argumentos = null;

        //sql += " ORDER BY " + CARRINHO_CAMPO_NOME_PRODUTO;

        Cursor cursor = db.rawQuery(sql, argumentos);

        int lenght = 0;

        if (cursor.moveToFirst()) {
            do {

                long id = cursor.getLong(cursor.getColumnIndex(CARRINHO_CAMPO_ID));
                String title = cursor.getString(cursor.getColumnIndex(CARRINHO_CAMPO_NOME_PRODUTO));
                int price = cursor.getInt(cursor.getColumnIndex(CARRINHO_CAMPO_PRECO_PRODUTO));
                String photo = cursor.getString(cursor.getColumnIndex(CARRINHO_CAMPO_FOTO_PRODUTO));
                String seller = cursor.getString(cursor.getColumnIndex(CARRINHO_CAMPO_VENDEDOR_PRODUTO));

                Produto produto = new Produto();
                produto.setId(id);
                produto.setTitle(title);
                produto.setPrice(price);
                produto.setThumbnailHd(photo);
                produto.setSeller(seller);

                ++lenght;

                produtoList.add(produto);
            } while (cursor.moveToNext());
        }

        cursor.close();
        Log.d("DEBUG","Lido:"+ lenght);
        db.close();

        return produtoList;
    }

    public long inserirTransacaoLocal(TransacaoLocal transacaoLocal) {

        SQLiteDatabase db = dataBaseSqlHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TRANSACOES_CAMPO_VALOR, transacaoLocal.getValor());
        values.put(TRANSACOES_CAMPO_DATA, transacaoLocal.getData());
        values.put(TRANSACOES_CAMPO_CARTAO, transacaoLocal.getNumCartao());
        values.put(TRANSACOES_CAMPO_CARTAO_NOME, transacaoLocal.getNomeCartao());

        long newRowId;
        newRowId = db.insert(
                TABELA_TRANSACOES,
                "",
                values);

        Log.d("DEBUG","Adicionou:"+ newRowId);

        db.close();
        return newRowId;
    }

    public int excluirTransacaoLocal(TransacaoLocal transacaoLocal) {

        SQLiteDatabase db = dataBaseSqlHelper.getWritableDatabase();

        String selection = TRANSACOES_CAMPO_ID + " LIKE ?";

        String[] selectionArgs = { String.valueOf(transacaoLocal.getId()) };

        int linhasAafetadas = db.delete(TABELA_TRANSACOES, selection, selectionArgs);

        db.close();

        return linhasAafetadas;
    }

    public List<TransacaoLocal> buscarTransacaoLocal() {

        List<TransacaoLocal> transacaoLocalList = new ArrayList<>();

        SQLiteDatabase db = dataBaseSqlHelper.getReadableDatabase();

        String sql = "SELECT * FROM " + TABELA_TRANSACOES;

        String[] argumentos = null;

        //sql += " ORDER BY " + CARRINHO_CAMPO_NOME_PRODUTO;

        Cursor cursor = db.rawQuery(sql, argumentos);

        int lenght = 0;

        if (cursor.moveToFirst()) {
            do {

                long id = cursor.getLong(cursor.getColumnIndex(TRANSACOES_CAMPO_ID));
                int valor = cursor.getInt(cursor.getColumnIndex(TRANSACOES_CAMPO_VALOR));
                String data = cursor.getString(cursor.getColumnIndex(TRANSACOES_CAMPO_DATA));
                String numCartao = cursor.getString(cursor.getColumnIndex(TRANSACOES_CAMPO_CARTAO));
                String nomeCartao = cursor.getString(cursor.getColumnIndex(TRANSACOES_CAMPO_CARTAO_NOME));

                TransacaoLocal transacaoLocal = new TransacaoLocal();
                transacaoLocal.setId(id);
                transacaoLocal.setValor(valor);
                transacaoLocal.setData(data);
                transacaoLocal.setNumCartao(numCartao);
                transacaoLocal.setNomeCartao(nomeCartao);

                ++lenght;

                transacaoLocalList.add(transacaoLocal);
            } while (cursor.moveToNext());
        }

        Log.d("DEBUG","Lido:"+ lenght);
        cursor.close();
        db.close();

        return transacaoLocalList;
    }

    public static class Store implements BaseColumns {
        // tabela para salvar as compras no carrinho
        public static final String TABELA_CARRINHO = "carrinho";
        public static final String CARRINHO_CAMPO_ID= "id_carrinho";

        public static final String CARRINHO_CAMPO_NOME_PRODUTO = "produto_carrinho";
        public static final String CARRINHO_CAMPO_PRECO_PRODUTO = "preco_carrinho";
        public static final String CARRINHO_CAMPO_VENDEDOR_PRODUTO = "vendedor_carrinho";
        public static final String CARRINHO_CAMPO_FOTO_PRODUTO = "foto_carrinho";

        // tabela para salvar as transacoes realizadas
        public static final String TABELA_TRANSACOES = "transacoes";
        public static final String TRANSACOES_CAMPO_ID = "id_transacoes";

        public static final String TRANSACOES_CAMPO_VALOR = "valor_transacoes";
        public static final String TRANSACOES_CAMPO_DATA = "data_transacoes";
        public static final String TRANSACOES_CAMPO_CARTAO = "cartao_transacoes";
        public static final String TRANSACOES_CAMPO_CARTAO_NOME = "nome_cartao_transacoes";
    }

}
