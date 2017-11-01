package com.uzias.starwarsshop.checkout.presentation.navigation

import com.uzias.starwarsshop.checkout.presentation.view.CheckoutActivity
import com.cooltechworks.creditcarddesign.CardEditActivity
import android.content.Intent

interface CheckoutNavigation {
    fun goToAddCreditCard()
}

class CheckoutNavigationImpl(private var view: CheckoutActivity): CheckoutNavigation {

    override fun goToAddCreditCard() {
        val intent = Intent(view, CardEditActivity::class.java)
        view.startActivityForResult(intent, CheckoutActivity.ADD_CREDIT_CARD_REQUEST_CODE)
    }

}
