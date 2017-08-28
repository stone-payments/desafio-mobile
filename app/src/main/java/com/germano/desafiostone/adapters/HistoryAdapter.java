package com.germano.desafiostone.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.germano.desafiostone.R;
import com.germano.desafiostone.models.History;
import com.germano.desafiostone.utils.FormatNumber;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by germano on 28/08/17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {


    private Context mContext;
    private RealmResults mHistories;

    public HistoryAdapter(Context context, RealmQuery histories) {
        mHistories = histories.findAll();
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textview_hold_name)
        TextView mTextViewHoldName;

        @BindView(R.id.textview_final_card_number)
        TextView mTextViewFinalCardNumber;

        @BindView(R.id.textview_value)
        TextView mTextViewValue;

        @BindView(R.id.textview_timestamp)
        TextView mTextViewTimeStamp;

        @BindView(R.id.cardview_history)
        CardView mCardViewHistory;

        History item;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(final History item) {
            this.item = item;
            mTextViewHoldName.setText(mContext.getString(R.string.card_name, item.getHoldName()));
            mTextViewFinalCardNumber.setText(mContext.getString(R.string.final_card, item.getCardNumber()));
            mTextViewValue.setText(mContext.getString(R.string.value, FormatNumber.set(item.getValue())));
            mTextViewTimeStamp.setText(mContext.getString(R.string.timestamp, item.getTimestamp()));

            animateFade(mCardViewHistory);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData((History) mHistories.get(position));
    }

    @Override
    public int getItemCount() {
        return mHistories.size();
    }


    private void animateFade(CardView cardView){
        cardView.setAlpha(0.3f);
        cardView.animate()
                .alpha(1)
                .setDuration(800)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }
}