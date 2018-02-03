package br.com.valdir.desafiolojastarwars.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by valdyrtorres on 26/11/2017.
 */

public class UsuariosContract {

    public static final String CONTENT_AUTHORITY = "br.com.valdir.desafiolojastarwars";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_USUARIOS = "usuarios";

    private UsuariosContract() {}

    public static abstract class UsuarioEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_USUARIOS).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USUARIOS;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USUARIOS;

        public static final String TABLE_NAME = "usuarios";

        public static final String _ID = "_id";
        public static final String COLUMN_NOME = "nome";

        public static Uri buildUriForUsuarios() {
            return CONTENT_URI.buildUpon().build();
        }

        public static Uri buildUriForUsuarios(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static long getIdFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }
    }

}
