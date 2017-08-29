package com.germano.desafiostone.services;


import com.germano.desafiostone.models.History;

import io.realm.RealmQuery;

/**
 * Created by germano on 28/08/17.
 */

public interface HistoryRealm {

    void save(History history);

    RealmQuery getAll();

    boolean isEmpty();
}
