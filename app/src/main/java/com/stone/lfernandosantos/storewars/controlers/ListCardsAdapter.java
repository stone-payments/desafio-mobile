package com.stone.lfernandosantos.storewars.controlers;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stone.lfernandosantos.storewars.R;
import com.stone.lfernandosantos.storewars.models.Card;

import java.util.List;

/**
 * Created by lf.fernandodossantos on 20/05/17.
 */

public class ListCardsAdapter extends BaseAdapter {

    private List<Card> cards;
    private Activity activity;
    private ImageView imgBandeira;
    private TextView txtNumberCard;
    private TextView txtNameCard;
    private TextView txtDateCard;

    public ListCardsAdapter(List<Card> cards, Activity activity) {
        this.cards = cards;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Object getItem(int position) {
        return cards.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v =  activity.getLayoutInflater().inflate(R.layout.item_card_adapter, parent, false);

        imgBandeira = (ImageView) v.findViewById(R.id.imageViewCardAdp);
        txtNumberCard = (TextView) v.findViewById(R.id.txtNumCardAdp);
        txtNameCard = (TextView) v.findViewById(R.id.txtNameCardAdp);
        txtDateCard = (TextView) v.findViewById(R.id.txtCardDateAdp);

        Glide.with(activity).load(R.drawable.ic_bandeira_visa).into(imgBandeira);
        txtNumberCard.setText(cards.get(position).numCard);
        txtNameCard.setText(cards.get(position).nome);
        txtDateCard.setText(cards.get(position).validade);

        return v;
    }
}
