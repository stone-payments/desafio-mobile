package br.com.valdir.desafiolojastarwars.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by valdyrtorres on 26/11/2017.
 */

public class ProdutosDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "desafiolojastarwars.db";

    public ProdutosDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlTableProdutos = "CREATE TABLE " + ProdutosContract.ProdutoEntry.TABLE_NAME + " (" +
                ProdutosContract.ProdutoEntry._ID + " INTEGER PRIMARY KEY, " +
                ProdutosContract.ProdutoEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                ProdutosContract.ProdutoEntry.COLUMN_PRICE + " REAL, " +
                ProdutosContract.ProdutoEntry.COLUMN_ZIPCODE + " TEXT NOT NULL, " +
                ProdutosContract.ProdutoEntry.COLUMN_SELLER + " TEXT NOT NULL, " +
                ProdutosContract.ProdutoEntry.COLUMN_THUMBNAILHD_PATH + " TEXT NOT NULL, " +
                ProdutosContract.ProdutoEntry.COLUMN_DATA + " TEXT NOT NULL " +
                ");";

        db.execSQL(sqlTableProdutos);

        String sqlTableUsuarios = "CREATE TABLE " + UsuariosContract.UsuarioEntry.TABLE_NAME + " (" +
                UsuariosContract.UsuarioEntry._ID + " INTEGER PRIMARY KEY, " +
                UsuariosContract.UsuarioEntry.COLUMN_NOME + " TEXT NOT NULL " +
                ");";

        db.execSQL(sqlTableUsuarios);

        String sqlTableTransacoes = "CREATE TABLE " + TransacoesContract.TransacaoEntry.TABLE_NAME + " (" +
                TransacoesContract.TransacaoEntry._ID + " INTEGER PRIMARY KEY, " +
                TransacoesContract.TransacaoEntry.COLUMN_USUARIO_ID + " TEXT NOT NULL, " +
                TransacoesContract.TransacaoEntry.COLUMN_VALOR + " REAL, " +
                TransacoesContract.TransacaoEntry.COLUMN_DATA + " TEXT NOT NULL, " +
                TransacoesContract.TransacaoEntry.COLUMN_HORA + " TEXT NOT NULL, " +
                TransacoesContract.TransacaoEntry.COLUMN_ULT_4_DIGITOS_CARTAO + " TEXT NOT NULL, " +
                TransacoesContract.TransacaoEntry.COLUMN_PORTADOR_CARTAO_NOME_COMPLETO + " TEXT NOT NULL " +
                ");";

        db.execSQL(sqlTableTransacoes);

        String sqlTableUsuariosSaldo = "CREATE TABLE " + UsuariosSaldoContract.UsuarioSaldoEntry.TABLE_NAME + " (" +
                UsuariosSaldoContract.UsuarioSaldoEntry._ID + " INTEGER PRIMARY KEY, " +
                UsuariosSaldoContract.UsuarioSaldoEntry.COLUMN_USUARIO_ID + " TEXT NOT NULL, " +
                UsuariosSaldoContract.UsuarioSaldoEntry.COLUMN_SALDO_ATUAL + " REAL " +
                ");";

        db.execSQL(sqlTableUsuariosSaldo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + UsuariosContract.UsuarioEntry.TABLE_NAME);

        onCreate(db);
    }
}
