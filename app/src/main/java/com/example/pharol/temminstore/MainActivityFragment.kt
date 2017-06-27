package com.example.pharol.temminstore

import android.app.AlertDialog
import android.app.Dialog
import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pharol.temminstore.item.ItemRecyclerAdapter
import com.example.pharol.temminstore.item.ItemViewModelFactory
import com.example.pharol.temminstore.shoppingcart.ShoppingCart
import com.example.pharol.temminstore.item.Item
import com.example.pharol.temminstore.item.ItemViewModel
import javax.inject.Inject
import android.content.Intent
import com.example.pharol.temminstore.di.ui.DaggerFragmentComponent
import com.example.pharol.temminstore.di.ui.FragmentModule
import com.example.pharol.temminstore.item.ItemDetailsActivity
import pharol.com.br.temminstore.di.*


/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : LifecycleFragment(), ItemRecyclerAdapter.onClickAddToCart {

    val component by lazy { DaggerFragmentComponent.builder().fragmentModule(FragmentModule(this)).
            applicationComponent((this.context.applicationContext as TemminApplication).applicationComponent).build() }

    var recyclerView: RecyclerView? = null

    @Inject lateinit var itemViewFactory : ItemViewModelFactory

    lateinit var  itemView: ItemViewModel

    private var adapter = ItemRecyclerAdapter(arrayListOf(),this)
    private var listener: MainActivityFragment.onItemAddedToCartListener? = null

    interface onItemAddedToCartListener{
        fun onItemAdded(size: Int)
    }

    override fun onClick(item: Item) {
        itemView.addItemToCart(item)
    }

    companion object{
        val  REQUEST_CODE = 1
    }

    override fun onImageClick(item: Item) {
        startActivityForResult(Intent(context, ItemDetailsActivity::class.java).putExtra("itemId",item.id  ),REQUEST_CODE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val returnView = inflater.inflate(R.layout.fragment_main,container,false)
        val layoutManager = GridLayoutManager(this.context,2)
        recyclerView = returnView.findViewById(R.id.rv_listview) as RecyclerView
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
        return returnView
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener =  context as onItemAddedToCartListener
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        itemView = ViewModelProviders.of(this,itemViewFactory).get(ItemViewModel::class.java)

        val dialog = onCreateDialog()
        itemView.getItemList().observe(this, Observer<List<Item>> { list ->
                if (list != null && list.isNotEmpty()){
                    adapter.itemList = list
                    adapter.notifyDataSetChanged()
                    dialog?.dismiss()
                } else{
                      dialog?.show()
                }
        })

        itemView.returnOngoingCart().observe(this, Observer<List<ShoppingCart>> { list ->
            if (list != null){
                itemView.init(list)
            }
        })

        itemView.getCartItems().observe(this, Observer {
            listener?.onItemAdded(it!!.size) })
    }

    override fun onPause() {
        super.onPause()
        save()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == REQUEST_CODE && data != null){
            val qtd = data.getIntExtra("qtd",0)
            itemView.getItem(data.getIntExtra("itemId",0)).observe(this, Observer {
                if (it != null) itemView.addItemToCart(it,qtd)
            })
        }
    }

    fun save(): Boolean = itemView.saveCart()

    fun onCreateDialog(): Dialog? = AlertDialog.Builder(context).setTitle(R.string.retry_dialog_title).
            setMessage("Can't get products on WebServer. Check if Internet is Ok and retry.")
                 .setPositiveButton(R.string.retry_dialog_retry, { _,_ ->
                     itemView.getItemList() })
                 .setNegativeButton(R.string.retry_dialog_quit, { _, _->
                     System.exit(0)})
                 .create()
}
