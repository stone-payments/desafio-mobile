package com.jademcosta.starstore.creditCard;


import android.content.Context;

import com.jademcosta.starstore.database.ItemsRepository;
import com.jademcosta.starstore.network.CreditCardApi;

public class CreditCardInjector {

    private Context context;

    public void inject(CreditCardActivity view, Context context) {
        this.context = context;

        CreditCardModel model = buildModel();
        CreditCardPresenter presenter = new CreditCardPresenter();

        model.setPresenter(presenter);
        view.setPresenter(presenter);
        presenter.setModel(model);
        presenter.setView(view);
    }

    private CreditCardModel buildModel() {
        return new CreditCardModel(new CreditCardApi(), new ItemsRepository(context));
    }
}
