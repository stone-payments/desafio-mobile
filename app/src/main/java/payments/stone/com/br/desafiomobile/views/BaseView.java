package payments.stone.com.br.desafiomobile.views;

import android.content.Context;

/**
 * Created by william.gouvea on 9/22/17.
 */

public interface BaseView<T> {
    void showLoading();

    void hideLoading();

    void showError(String error);

    void hideError();

    T handleIntent();

    void loadViews();

    Context context();
}
