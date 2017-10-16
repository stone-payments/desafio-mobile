package jb.project.starwarsshoppinglist.cart.activity

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import android.widget.Toast
import io.realm.Realm
import jb.project.starwarsshoppinglist.BaseActivity
import jb.project.starwarsshoppinglist.R
import jb.project.starwarsshoppinglist.Utils.DividerItemDecoration.Companion.VERTICAL
import jb.project.starwarsshoppinglist.cart.`interface`.ItemClickListener
import jb.project.starwarsshoppinglist.cart.adapter.AdapterItemsRecycler
import jb.project.starwarsshoppinglist.model.Cart
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlin.properties.Delegates


class CheckoutActivity : BaseActivity(), ItemClickListener {

    private var realm: Realm by Delegates.notNull()
    private var mSumItems: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        setSupportActionBar(toolbar)
        realm = Realm.getDefaultInstance()

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {

        val cartItems = realm.where(Cart::class.java).findAll()!!
        if (cartItems.size > 0) {
            val adapter: AdapterItemsRecycler = AdapterItemsRecycler(cartItems)
            recycler_cart.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            recycler_cart.adapter = adapter
            adapter.setItemClickListener(this)
            recycler_cart.addItemDecoration(DividerItemDecoration(this, VERTICAL))

            for (item: Cart in cartItems) {
                mSumItems += item.quantity?.times(item.price!!) ?: 0
            }

            txt_sum_items.text = "R$ " + String.format("%.2f", mSumItems.div(100.0))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close() // Remember to close Realm when done.
    }

    override fun onClick(title: String?) {
        Toast.makeText(this, title, Toast.LENGTH_LONG).show()
        realm.executeTransactionAsync({ bgRealm ->
            bgRealm.where(Cart::class.java).equalTo("title", title).findFirst()?.deleteFromRealm()
        }, {
            // Transaction was a success.
        }) { error ->
            // Transaction failed and was automatically canceled.
        }
    }
}
