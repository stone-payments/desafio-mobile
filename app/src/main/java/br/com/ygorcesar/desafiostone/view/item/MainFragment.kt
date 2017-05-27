package br.com.ygorcesar.desafiostone.view.item

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ygorcesar.desafiostone.R
import br.com.ygorcesar.desafiostone.data.ApiDesafioMobile
import br.com.ygorcesar.desafiostone.data.hideProgress
import br.com.ygorcesar.desafiostone.data.layoutGrid
import br.com.ygorcesar.desafiostone.data.replaceToStack
import br.com.ygorcesar.desafiostone.model.Item
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)

        val progress_container = rootView.findViewById(R.id.progress_container)
        progress_container?.visibility = View.VISIBLE
        ApiDesafioMobile().api.getItems()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it -> setupRecyclerView(it) },
                        { error ->
                            error.printStackTrace()
                            hideProgress()
                        }, { hideProgress() })
        return rootView
    }

    fun setupRecyclerView(items: ArrayList<Item>) {
        rv_items.layoutGrid()
        rv_items.adapter = AdapterItems(items, { goToItemDetalFragment(it) })
    }

    fun goToItemDetalFragment(item: Item) {
        val args = android.os.Bundle()
        args.putString(ITEM_TITLE, item.title)
        args.putString(ITEM_SELLER, item.seller)
        args.putString(ITEM_THUMB_URL, item.thumbnailHd)
        args.putDouble(ITEM_PRICE, item.price)

        val fragment = ItemDetailFragment()
        fragment.arguments = args
        replaceToStack(fragment,R.id.fragment)
    }

    companion object {
        val ITEM_TITLE = "TITLE"
        val ITEM_SELLER = "SELLER"
        val ITEM_PRICE = "PRICE"
        val ITEM_THUMB_URL = "THUMB_URL"
    }
}
