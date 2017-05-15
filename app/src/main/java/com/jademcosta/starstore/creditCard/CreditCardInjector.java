package com.jademcosta.starstore.creditCard;


public class CreditCardInjector {

    public void inject(CreditCardActivity view) {

        CreditCardModel model = buildModel();
        CreditCardPresenter presenter = new CreditCardPresenter();

        model.setPresenter(presenter);
        view.setPresenter(presenter);
        presenter.setModel(model);
        presenter.setView(view);
    }

    private CreditCardModel buildModel() {
        return new CreditCardModel();
    }
}
