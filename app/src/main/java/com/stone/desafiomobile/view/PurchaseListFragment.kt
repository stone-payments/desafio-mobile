package com.stone.desafiomobile.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.stone.desafiomobile.R
import com.stone.desafiomobile.di.DaggerInjectionComponent
import com.stone.desafiomobile.di.DatabaseModule
import com.stone.desafiomobile.di.RetrofitModule
import com.stone.desafiomobile.model.PurchaseLog
import com.stone.desafiomobile.viewmodel.PurchaseListVm


class PurchaseListFragment : Fragment() {

    lateinit internal var mViewModel: PurchaseListVm
    lateinit internal var mRecyclerView: RecyclerView
    lateinit internal var mAdapter: PurchaseListAdapter

    lateinit internal var mEmptyListTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProviders.of(this).get(PurchaseListVm::class.java)


        val injectionComponent = DaggerInjectionComponent.builder()
                .retrofitModule(RetrofitModule())
                .databaseModule(DatabaseModule(activity))
                .build()
        injectionComponent.inject(mViewModel)

        mViewModel.purchaseLogList.observe(this, Observer<List<PurchaseLog>> { purchaseLog ->
            if (purchaseLog != null) {
                mAdapter.mValues = purchaseLog
                if (!purchaseLog.isEmpty()) {
                    mEmptyListTV.visibility = View.GONE
                }
            }
        })

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_purchase_list, container, false)

        mRecyclerView = view.findViewById(R.id.purchase_list)

        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mAdapter = PurchaseListAdapter()
        mRecyclerView.adapter = mAdapter

        mEmptyListTV = view.findViewById(R.id.empty_list_text)

        return view
    }

}
