package jb.project.starwarsshoppinglist.model

import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*


/**
 * Created by Jb on 15/10/2017.
 */
open class Cart(
        @PrimaryKey var title: String? = null,
        var price: Int? = null,
        var thumbnailHd: String? = null,
        var quantity: Int? = null,
        private var dateInserted: Date = Date()) : RealmObject() {

    override fun toString(): String {
        return "Cart(title=$title, price=$price, quantity=$quantity, dateInserted=$dateInserted)"
    }

    fun delete(realm: Realm, id: String) {
        realm.where(Cart::class.java).equalTo("title", id).findFirst()?.deleteFromRealm()
    }

    fun updateAmount(realm: Realm, id: String, amount : Int)
    {
        realm.where(Cart::class.java).equalTo("title", id).findFirst()?.quantity = amount

    }

    fun deleteAll(realm: Realm) {
        realm.where(Cart::class.java).findAll().deleteAllFromRealm()
    }

}