package br.com.ygorcesar.desafiostone.view.item

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ygorcesar.desafiostone.R
import br.com.ygorcesar.desafiostone.data.*
import br.com.ygorcesar.desafiostone.model.Item
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgress()
        ApiDesafioMobile().api.getItems()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it -> setupRecyclerView(it) },
                        { error ->
                            error.printStackTrace()
                            toast(R.string.problem)
                            hideProgress()
                        }, { hideProgress() })
    }

    fun setupRecyclerView(items: ArrayList<Item>) {
        rv_items.layoutGrid()
        rv_items.adapter = AdapterItems(items, { goToItemDetalFragment(it) })
    }

    fun goToItemDetalFragment(item: Item) {
        val args = Bundle()
        args.putString(KEY_ITEM, Gson().toJson(item))

        val fragment = ItemDetailFragment()
        fragment.arguments = args
        replaceToStack(fragment, R.id.fragment)
    }

    companion object {
        val KEY_ITEM = "ITEM_ARGS"
    }
}
