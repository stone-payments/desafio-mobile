package kelly.com.desafiostone.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by kelly on 30/11/17.
 */

public class ShopContract {
    public static final String CONTENT_AUTHORITY = "kelly.com.desafiostone";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_TRANSACTIONS = "transaction";

    private ShopContract(){}

    public static final class TransactionEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TRANSACTIONS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRANSACTIONS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRANSACTIONS;

        public final static String TABLE_NAME = "transaction";

        public final static String _ID = BaseColumns._ID;
        /**
         * Name of the credit card holder.
         *
         * Type: TEXT
         */
        public final static String COLUMN_TRANSACTION_HOLDER_NAME ="holderName";
        /**
         * Last four digits of the credit card number
         *
         * Type: TEXT
         */
        public final static String COLUMN_TRANSACTION_LAST_CARD_NUMBERS ="lastCardNumbers";
        /**
         * The value of the transaction
         *
         * Type: REAL
         */
        public final static String COLUMN_TRANSACTION_VALUE = "value";
        /**
         * The transaction date in miliseconds
         *
         * Type: INT
         */
        public final static String COLUMN_TRANSACTION_DATE = "date";
    }
}
