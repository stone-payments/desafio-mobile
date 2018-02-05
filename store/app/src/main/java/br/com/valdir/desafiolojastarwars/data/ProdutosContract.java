package br.com.valdir.desafiolojastarwars.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class ProdutosContract {

    public static final String CONTENT_AUTHORITY = "br.com.valdir.desafiolojastarwars";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PRODUTOS = "produtos";

    private ProdutosContract() {}

    public static abstract class ProdutoEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PRODUTOS).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUTOS;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUTOS;

        public static final String TABLE_NAME = "produtos";

        public static final String _ID = "_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_ZIPCODE = "zipcode";
        public static final String COLUMN_SELLER = "seller";
        public static final String COLUMN_THUMBNAILHD_PATH = "thumbnailHdPath";
        public static final String COLUMN_DATA = "data";

        public static Uri buildUriForProdutos() {
            return CONTENT_URI.buildUpon().build();
        }

        public static Uri buildUriForProdutos(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static long getIdFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }
    }

}
