package com.germano.desafiostone.services;

import android.content.Context;

import com.germano.desafiostone.models.History;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * Created by germano on 28/08/17.
 */

public class HistoryRealmImpl implements HistoryRealm {

    private Realm mRealm;

    public HistoryRealmImpl(Context context) {
        Realm.init(context);
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public boolean isEmpty() {
        return mRealm.isEmpty();
    }

    @Override
    public void save(History history) {
        mRealm.beginTransaction();
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(new Date());

        History mHistory = mRealm.createObject(History.class, timeStamp);
        mHistory.setCardNumber(history.getCardNumber());
        mHistory.setValue(history.getValue());
        mHistory.setHoldName(history.getHoldName());

        mRealm.commitTransaction();
        mRealm.close();
    }

    @Override
    public RealmQuery getAll() {
        return mRealm.where(History.class);
    }
}
