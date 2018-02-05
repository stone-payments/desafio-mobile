package br.com.valdir.desafiolojastarwars;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import br.com.valdir.desafiolojastarwars.data.TransacoesContract;
import br.com.valdir.desafiolojastarwars.data.TransacoesDBHelper;

public class AdminActivity extends AppCompatActivity {

    private ListView listaTransacoes;
    private SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.listagem_dados);

        TransacoesDBHelper crud = new TransacoesDBHelper(this);
        final Cursor cursor = crud.carregaDados(db, this);

        String[] nomeCampos = new String[] { TransacoesContract.TransacaoEntry._ID,
                TransacoesContract.TransacaoEntry.COLUMN_USUARIO_ID,
                TransacoesContract.TransacaoEntry.COLUMN_VALOR,
                TransacoesContract.TransacaoEntry.COLUMN_DATA,
                TransacoesContract.TransacaoEntry.COLUMN_HORA,
                TransacoesContract.TransacaoEntry.COLUMN_ULT_4_DIGITOS_CARTAO,
                TransacoesContract.TransacaoEntry.COLUMN_PORTADOR_CARTAO_NOME_COMPLETO };

        int[] idViews = new int[] { R.id.id_transacao, R.id.id_usuario, R.id.campo_valor,
                                    R.id.campo_data, R.id.campo_hora, R.id.campo_cc4digitos,
                                    R.id.campo_cartao_nome_completo};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this,
                R.layout.item_transacao, cursor, nomeCampos, idViews, 0);

        listaTransacoes = findViewById(R.id.lista_transacoes);
        listaTransacoes.setAdapter(adaptador);

//        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String codigo;
//                cursor.moveToPosition(position);
//                codigo = cursor.getString(cursor.getColumnIndexOrThrow(TransacoesContract.TransacaoEntry._ID));
//                Intent intent = new Intent(AdminActivity.this, Alterar.class);
//                intent.putExtra("codigo", codigo);
//                startActivity(intent);
//                finish();
//            }
//        });

    }

}
