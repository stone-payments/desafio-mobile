package payments.stone.com.br.desafiomobile;

import java.util.Collections;
import java.util.List;

/**
 * Created by william.gouvea on 9/21/17.
 */

class ProductsResponse {
    public final  List<Product> productList;

    ProductsResponse(List<Product> productList) {
        this.productList = Collections.unmodifiableList(productList);
    }
}
