package payments.stone.com.br.desafiomobile;

import android.content.Context;

/**
 * Created by william.gouvea on 9/22/17.
 */

public interface BaseView {
    void showLoading();

    void hideLoading();

    void showError(String error);

    void hideError();

    void loadViews();

    Context context();
}
