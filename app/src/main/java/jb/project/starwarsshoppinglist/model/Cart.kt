package jb.project.starwarsshoppinglist.model

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

    open class Cart

    override fun toString(): String {
        return "Cart(title=$title, price=$price, quantity=$quantity, dateInserted=$dateInserted)"
    }

}