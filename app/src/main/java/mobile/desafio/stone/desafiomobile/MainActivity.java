package mobile.desafio.stone.desafiomobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeScreen_Buy(View v){
        Intent intent = new Intent(this, BuyActivity.class);
        startActivity(intent);
    }

    public void changeScreen_Transition(View v){
        Intent i = new Intent(this, TransactionsActivity.class);
        startActivity(i);
    }
}
