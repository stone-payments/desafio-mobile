package payments.stone.com.br.desafiomobile;

/**
 * Created by william.gouvea on 9/22/17.
 */

public interface DetailsView {
    void showDetails(Product product);

    public void showLoading();

    public void hideLoading();

    public void showError(String error);

    public void hideError();
}
