package com.stone.lfernandosantos.storewars.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.stone.lfernandosantos.storewars.R;
import com.stone.lfernandosantos.storewars.controlers.ListCardsAdapter;
import com.stone.lfernandosantos.storewars.models.Card;
import com.stone.lfernandosantos.storewars.models.CardDAO;

import java.util.List;

public class ListCardsActivity extends AppCompatActivity {

    private ListView listViewCards;
    private List<Card> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cards);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Cartões");
        listViewCards = (ListView) findViewById(R.id.listSavedCards);

        CardDAO dao = new CardDAO(this);
        cards = dao.getCards();
        if (cards != null && cards.size() > 0 ) {
            ListCardsAdapter adapter = new ListCardsAdapter(cards, this);
            listViewCards.setAdapter(adapter);
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddCard);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListCardsActivity.this, CardDataActivity.class));
            }
        });
    }

}
