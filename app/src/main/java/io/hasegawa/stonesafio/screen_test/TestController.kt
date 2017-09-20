package io.hasegawa.stonesafio.screen_test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.jakewharton.rxbinding2.view.clicks
import io.hasegawa.presentation.screen_test.TestContract
import io.hasegawa.presentation.screen_test.TestContract.StateEvent
import io.hasegawa.presentation.screen_test.TestContract.ViewState
import io.hasegawa.presentation.screen_test.TestPresenter
import io.hasegawa.stonesafio.R
import io.hasegawa.stonesafio.common.BaseController
import io.hasegawa.stonesafio.common.bindView
import io.reactivex.Observable


class TestController(bundle: Bundle)
    : BaseController<TestContract.View, ViewState, StateEvent, TestPresenter>(bundle), TestContract.View {
    companion object {
        private val BKEY_ID = "test-controller-id"
    }

    private val titleTv: TextView by bindView(R.id.title_tv)
    private val reloadBt: Button by bindView(R.id.reload_bt)
    private val nextScreenBt: Button by bindView(R.id.next_screen_bt)
    private val textTv: TextView by bindView(R.id.text_tv)

    val titleId: Int

    constructor(titleNumber: Int) : this(Bundle().apply { putInt(BKEY_ID, titleNumber) })

    init {
        titleId = bundle.getInt(BKEY_ID)
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup, savedViewState: Bundle?): View {
        Log.d("TEST", "inflateView")
        return inflater.inflate(R.layout.screen_main, container, false)
    }

    override fun createPresenter(): TestPresenter {
        Log.d("TEST", "Creating Presenter")
        return TestDIComponent.getPresenter(this)
    }

    override fun onViewBound() {
        titleTv.text = "ID: $titleId"
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        Log.d("TEST", "Attached")
    }

    override fun render(state: ViewState) {
        when (state) {
            is ViewState.LoadingData -> {
                textTv.text = "Loading"
            }
            is ViewState.Okay -> {
                textTv.text = state.text
            }
            is ViewState.Error -> {
                textTv.text = state.error
            }
        }
    }

    override fun reloadData(): Observable<Unit> = reloadBt.clicks()
    override fun nextScreen(): Observable<Unit> = nextScreenBt.clicks()
}