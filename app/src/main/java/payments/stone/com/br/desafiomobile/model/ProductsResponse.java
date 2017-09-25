package payments.stone.com.br.desafiomobile.model;

import java.util.Collections;
import java.util.List;

/**
 * Created by william.gouvea on 9/21/17.
 */

public class ProductsResponse {
    public List<Product> productList;

    public ProductsResponse(List<Product> productList) {
        this.productList = Collections.unmodifiableList(productList);
    }
}
