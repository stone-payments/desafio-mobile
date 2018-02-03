package br.com.valdir.desafiolojastarwars.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by valdyrtorres on 26/11/2017.
 */

public class UsuariosSaldoContract {

    public static final String CONTENT_AUTHORITY = "br.com.valdir.desafiolojastarwars";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_USUARIOS_SALDO = "usuario_saldo";

    private UsuariosSaldoContract() {}

    public static abstract class UsuarioSaldoEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_USUARIOS_SALDO).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USUARIOS_SALDO;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USUARIOS_SALDO;

        public static final String TABLE_NAME = "usuario_saldo";

        public static final String _ID = "_id";
        public static final String COLUMN_USUARIO_ID = "usuario_id";
        public static final String COLUMN_SALDO_ATUAL = "saldo_atual";

        public static Uri buildUriForUsuariosSaldo() {
            return CONTENT_URI.buildUpon().build();
        }

        public static Uri buildUriForUsuariosSaldo(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static long getIdFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }
    }

}
