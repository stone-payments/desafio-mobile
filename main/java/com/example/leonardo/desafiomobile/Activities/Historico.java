package com.example.leonardo.desafiomobile.Activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.leonardo.desafiomobile.Objetos.Database;
import com.example.leonardo.desafiomobile.R;
import com.example.leonardo.desafiomobile.auxiliares.DbHandler;

public class Historico extends AppCompatActivity {

    String s;
    DbHandler db;
    TextView tv1;
    SQLiteDatabase s1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        tv1 = (TextView) findViewById(R.id.database);
        db = new DbHandler(this,"Finalmente.db",null,1);
        s1 = db.getReadableDatabase();
        s = db.getTableAsString(s1);
        tv1.setText(s);
        tv1.setMovementMethod(new ScrollingMovementMethod());

    }

    public void voltar(View view){
        Intent i1 = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i1);
        finish();
    }
}