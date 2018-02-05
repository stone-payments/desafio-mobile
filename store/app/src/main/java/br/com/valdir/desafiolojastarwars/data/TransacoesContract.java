package br.com.valdir.desafiolojastarwars.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class TransacoesContract {

    public static final String CONTENT_AUTHORITY = "br.com.valdir.desafiolojastarwars";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_TRANSACOES = "transacoes";

    private TransacoesContract() {}

    public static abstract class TransacaoEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRANSACOES).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRANSACOES;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRANSACOES;

        public static final String TABLE_NAME = "transacoes";

        public static final String _ID = "_id";
        public static final String COLUMN_USUARIO_ID = "usuario_id";
        public static final String COLUMN_VALOR = "valor";
        public static final String COLUMN_DATA = "data";
        public static final String COLUMN_HORA = "hora";
        public static final String COLUMN_ULT_4_DIGITOS_CARTAO = "number_cartao_last4";
        public static final String COLUMN_PORTADOR_CARTAO_NOME_COMPLETO = "portador_cartao_nome_completo";

        public static Uri buildUriForTransacoes() {
            return CONTENT_URI.buildUpon().build();
        }

        public static Uri buildUriForTransacoes(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static long getIdFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }
    }

}
