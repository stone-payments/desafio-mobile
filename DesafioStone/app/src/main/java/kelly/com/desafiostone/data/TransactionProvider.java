package kelly.com.desafiostone.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by kelly on 30/11/17.
 */

public class TransactionProvider extends ContentProvider {

    public static final String LOG_TAG = TransactionProvider.class.getSimpleName();

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private TransactionDataBaseHelper mDbHelper;
    private static final int TRANSACTIONS = 100;
    private static final int TRANSACTION_ID = 101;

    static {
        sUriMatcher.addURI(TransactionContract.CONTENT_AUTHORITY, TransactionContract.PATH_TRANSACTIONS, TRANSACTIONS);
        sUriMatcher.addURI(TransactionContract.CONTENT_AUTHORITY, TransactionContract.PATH_TRANSACTIONS+"/#", TRANSACTION_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new TransactionDataBaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);

        switch(match) {
            case TRANSACTIONS:
                cursor = db.query(TransactionContract.TransactionEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case TRANSACTION_ID:
                selection = TransactionContract.TransactionEntry._ID + " = ?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(TransactionContract.TransactionEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI: " + uri);

        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch(match) {
            case TRANSACTIONS:
                return insertTransaction(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertTransaction(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String holderName = contentValues.getAsString(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_HOLDER_NAME);
        if (holderName == null) {
            throw new IllegalArgumentException("Transactions requires a holder name");
        }

        String cardNumber = contentValues.getAsString(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_LAST_CARD_NUMBERS);
        if (cardNumber == null) {
            throw new IllegalArgumentException("Transaction requires a card number");
        } else if (cardNumber.length() > 4){
            throw new IllegalArgumentException("card number can't be bigger than 4");
        }

        Double value = contentValues.getAsDouble(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_VALUE);
        if (value == null || value < 0.00) {
            throw new IllegalArgumentException("Transaction requires value not negative");
        }

        long date = contentValues.getAsLong(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_DATE);
        if (date < 0) {
            throw new IllegalArgumentException("Transaction requires date not negative");
        }

        long id = db.insert(TransactionContract.TransactionEntry.TABLE_NAME, null, contentValues);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case TRANSACTIONS:
                return updateTransaction(uri, contentValues, selection, selectionArgs);
            case TRANSACTION_ID:
                selection = TRANSACTION_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateTransaction(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateTransaction(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_HOLDER_NAME)) {
            String holderName = values.getAsString(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_HOLDER_NAME);
            if (holderName == null) {
                throw new IllegalArgumentException("Transactions requires a holder name");
            }
        }

        if (values.containsKey(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_LAST_CARD_NUMBERS)) {

            String cardNumber = values.getAsString(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_LAST_CARD_NUMBERS);
            if (cardNumber == null) {
                throw new IllegalArgumentException("Transaction requires a card number");
            } else if (cardNumber.length() > 4){
                throw new IllegalArgumentException("card number can't be bigger than 4");
            }
        }

        if (values.containsKey(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_VALUE)) {
            Double value = values.getAsDouble(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_VALUE);
            if (value == null || value < 0.00) {
                throw new IllegalArgumentException("Transaction requires value not negative");
            }
        }

        if (values.containsKey(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_DATE)) {
            long date = values.getAsLong(TransactionContract.TransactionEntry.COLUMN_TRANSACTION_DATE);
            if (date < 0) {
                throw new IllegalArgumentException("Transaction requires date not negative");
            }
        }

        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int numRowsUpdated =  db.update(TransactionContract.TransactionEntry.TABLE_NAME, values, selection, selectionArgs);

        if(numRowsUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        int numRowsDeleted = 0;

        switch (match) {
            case TRANSACTIONS:
                numRowsDeleted = database.delete(TransactionContract.TransactionEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case TRANSACTION_ID:
                selection = TransactionContract.TransactionEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                numRowsDeleted =  database.delete(TransactionContract.TransactionEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if(numRowsDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numRowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TRANSACTIONS:
                return TransactionContract.TransactionEntry.CONTENT_LIST_TYPE;
            case TRANSACTION_ID:
                return TransactionContract.TransactionEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
