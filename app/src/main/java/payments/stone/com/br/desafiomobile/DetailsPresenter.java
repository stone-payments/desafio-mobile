package payments.stone.com.br.desafiomobile;

/**
 * Created by william.gouvea on 9/22/17.
 */

public class DetailsPresenter {
    private DetailsView mView;

    public DetailsPresenter(DetailsView mView) {
        this.mView = mView;
    }

    public DetailsPresenter loadProduct(Product product){
        if(product != null) {
            this.mView.showDetails(product);
        }
        return this;
    }
}
