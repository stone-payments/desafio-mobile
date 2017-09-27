package payments.stone.com.br.desafiomobile.data;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import payments.stone.com.br.desafiomobile.model.Order;
import payments.stone.com.br.desafiomobile.model.Product;

/**
 * Created by william.gouvea on 9/25/17.
 */

public class ShopRepository {

    private final Realm realm;

    public ShopRepository(Realm realm) {
        this.realm = realm;
    }

    public void save(final Order order) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(order);
            }
        });
    }

    public List<Order> findAllOrders(boolean detached){
        RealmResults<Order> realmResults = realm.where(Order.class).findAllSorted("transactionDate", Sort.DESCENDING);

        if(detached) {
            return realm.copyFromRealm(realmResults);
        } else {
            return realmResults;
        }
    }

    public void save(final List<Product> products) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(products);
            }
        });
    }

    public List<Product> findAllProducts(boolean detached){
        RealmResults<Product> realmResults = realm.where(Product.class).findAll();

        if(detached) {
            return realm.copyFromRealm(realmResults);
        } else {
            return realmResults;
        }
    }

}
