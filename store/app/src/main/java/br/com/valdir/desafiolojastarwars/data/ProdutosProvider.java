package br.com.valdir.desafiolojastarwars.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ProdutosProvider extends ContentProvider {

    private static final UriMatcher URI_MATCHER = buildUriMatcher();

    private ProdutosDBHelper dbHelper;

    private static final int PRODUTO = 100;

    private static final int PRODUTO_ID = 101;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(ProdutosContract.CONTENT_AUTHORITY, ProdutosContract.PATH_PRODUTOS, PRODUTO);
        uriMatcher.addURI(ProdutosContract.CONTENT_AUTHORITY, ProdutosContract.PATH_PRODUTOS + "/#", PRODUTO_ID);

        return uriMatcher;

    }

    @Override
    public boolean onCreate() {
        dbHelper = new ProdutosDBHelper(getContext());

        return true;
    }

    @Nullable

    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase readableDatabase = dbHelper.getReadableDatabase();

        Cursor cursor;

        switch(URI_MATCHER.match(uri)) { // O match devolve o id direto
            case PRODUTO:
                cursor = readableDatabase.query(ProdutosContract.ProdutoEntry.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PRODUTO_ID:
                selection = ProdutosContract.ProdutoEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ProdutosContract.ProdutoEntry.getIdFromUri(uri))};

                cursor = readableDatabase.query(ProdutosContract.ProdutoEntry.TABLE_NAME,
                        projection, selection, selectionArgs, null,null,sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Uri não identificada: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case PRODUTO:
                return ProdutosContract.ProdutoEntry.CONTENT_TYPE;
            case PRODUTO_ID:
                return ProdutosContract.ProdutoEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Uri não identificada: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();

        long id;
        switch (URI_MATCHER.match(uri)) {
            case PRODUTO:
                id = writableDatabase.insert(ProdutosContract.ProdutoEntry.TABLE_NAME,null, values);

                if (id == -1) {
                    return null;
                }
                break;
            default:
                throw new IllegalArgumentException("Uri não identificada: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ProdutosContract.ProdutoEntry.buildUriForProdutos(id);

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();

        int delete = 0;
        switch (URI_MATCHER.match(uri)) {
            case PRODUTO:
                delete = writableDatabase.delete(ProdutosContract.ProdutoEntry.TABLE_NAME, selection, selectionArgs);
            case PRODUTO_ID:
                selection = ProdutosContract.ProdutoEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ProdutosContract.ProdutoEntry.getIdFromUri(uri))};

                delete = writableDatabase.delete(ProdutosContract.ProdutoEntry.TABLE_NAME, selection, selectionArgs);
        }

        if (delete != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return delete;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();

        int update = 1;

        switch (URI_MATCHER.match(uri)) {
            case PRODUTO:
                update = writableDatabase.update(ProdutosContract.ProdutoEntry.TABLE_NAME, values, selection, selectionArgs);
            case PRODUTO_ID:
                selection = ProdutosContract.ProdutoEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ProdutosContract.ProdutoEntry.getIdFromUri(uri))};

                update = writableDatabase.update(ProdutosContract.ProdutoEntry.TABLE_NAME, values, selection, selectionArgs);
        }

        if (update != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return update;
    }
}
