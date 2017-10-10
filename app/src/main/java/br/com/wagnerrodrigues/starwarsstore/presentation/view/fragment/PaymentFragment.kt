package br.com.wagnerrodrigues.starwarsstore.presentation.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.wagnerrodrigues.starwarsstore.presentation.event.ErrorEvent
import br.com.wagnerrodrigues.starwarsstore.presentation.presenter.PaymentPresenter
import br.com.wagnerrodrigues.starwarsstore.presentation.view.activity.MainActivity
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_payment.*
import starwarsstore.wagnerrodrigues.com.br.starwarsstore.R

/**
 * Fragment do pagamento
 */
class PaymentFragment : Fragment() {

    private val presenter: PaymentPresenter = PaymentPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).shouldDisplayHomeUp()
        presenter.registerEvents()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tx_total_payment.text = arguments?.getString("totalPurchase")
        activity.toolbar.title = getString(R.string.payment_title);
        bt_finish_purchase.setOnClickListener{
            presenter.finishPurchase()
        }
    }

    override fun onDestroy() {
        presenter.unregisterEvents()
        super.onDestroy()
    }

    fun showLoadingButton(){
        bt_finish_purchase.isEnabled = false
        pb_finish_payment.visibility = View.VISIBLE
        bt_finish_purchase.visibility = View.GONE
    }

    fun hideLoadingButton(){
        bt_finish_purchase.isEnabled = true
        bt_finish_purchase.visibility = View.VISIBLE
        pb_finish_payment.visibility = View.GONE
    }

    fun onSuccessfullPurchase() {

        for (i in 0 until activity.supportFragmentManager.backStackEntryCount) {
            activity.supportFragmentManager.popBackStack()
        }
        hideLoadingButton()
        Toast.makeText(this.context,getString(R.string.payment_success_message), Toast.LENGTH_SHORT).show()
    }

    fun onUnsuccessfullPurchase(errorEvent: ErrorEvent) {
        Toast.makeText(this.context,errorEvent.message, Toast.LENGTH_SHORT).show()
        hideLoadingButton()
    }
}