package com.example.pharol.temminstore.item

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.pharol.temminstore.MainActivityFragment
import com.example.pharol.temminstore.R
import com.example.pharol.temminstore.util.toMoneyString
import com.squareup.picasso.Picasso
import com.example.pharol.temminstore.TemminApplication
import com.example.pharol.temminstore.di.ui.ActivityModule
import com.example.pharol.temminstore.di.ui.DaggerActivityComponent
import com.example.pharol.temminstore.shoppingcart.ShoppingCartViewModelFactory
import java.math.BigDecimal
import javax.inject.Inject

class ItemDetailsActivity : AppCompatActivity(), LifecycleRegistryOwner {

    val component    by lazy { DaggerActivityComponent.builder().activityModule(ActivityModule(this)).
            applicationComponent((application as TemminApplication).applicationComponent).build() }

    @Inject lateinit var itemViewFactory : ItemViewModelFactory
    @Inject lateinit var cartViewFactory : ShoppingCartViewModelFactory

    val tv_cartItemTitle by lazy { findViewById(R.id.tv_item_details_title) as TextView }
    val tv_cartItemPrice by lazy { findViewById(R.id.tv_item_details_price) as TextView }
    val tv_cartItemQuantity by lazy { findViewById(R.id.tv_item_details_quantity) as TextView }
    val tv_cartItemPriceTotal by lazy { findViewById(R.id.tv_item_details_total_price) as TextView }
    val bt_plus by lazy { findViewById(R.id.bt_item_details_plus) as Button }
    val bt_minus by lazy { findViewById(R.id.bt_item_details_minus) as Button }
    val bt_add by lazy { findViewById(R.id.bt_add_to_cart) as Button }
    val iv_ItemPhoto by lazy { findViewById(R.id.iv_item_details_photo) as ImageView }
    var mRegistry = LifecycleRegistry(this)
    var qtd = 0
    var price: BigDecimal = BigDecimal.ZERO

    override fun getLifecycle(): LifecycleRegistry {
        return this.mRegistry
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)
        component.inject(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val itemId = intent.getIntExtra("itemId",0)
        val itemViewModel = ViewModelProviders.of(this,itemViewFactory).get(ItemViewModel::class.java)

        itemViewModel.getItem(itemId).observe(this, Observer<Item>{
            tv_cartItemTitle.text = it?.title
            price = it?.price!!
            tv_cartItemPrice.text = it.price.toMoneyString()
            tv_cartItemQuantity.text = qtd.toString()
            tv_cartItemPriceTotal.text = BigDecimal.ZERO.toMoneyString()
            Picasso.with(this).load(it.thumbnailHd)?.error(R.drawable.ic_not_found)?.into(iv_ItemPhoto)

            bt_plus.setOnClickListener({
                qtd+=1
                tv_cartItemQuantity.text = qtd.toString()
                tv_cartItemPriceTotal.text = price.multiply(BigDecimal(qtd)).toMoneyString()
            })

            bt_minus.setOnClickListener({
                if ( qtd >0){
                    qtd-=1
                    tv_cartItemQuantity.text = qtd.toString()
                    tv_cartItemPriceTotal.text = price.multiply(BigDecimal(qtd)).toMoneyString()
                }
            })

            bt_add.setOnClickListener({
                setResult(MainActivityFragment.REQUEST_CODE, Intent().putExtra("qtd",qtd).putExtra("itemId",itemId))
                finish()
            })

        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
