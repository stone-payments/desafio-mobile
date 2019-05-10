package br.com.stone.vianna.starstore.view.card

import br.com.stone.vianna.starstore.extensions.addThreads
import br.com.stone.vianna.starstore.extensions.parser
import br.com.stone.vianna.starstore.entity.PaymentRequest
import br.com.stone.vianna.starstore.entity.PaymentTransaction

interface PaymentRepository {

    fun checkout(paymentRequest: PaymentRequest,
                 onSuccess: ((PaymentTransaction) -> Unit)? = null,
                 onError: ((error: String) -> Unit)? = null)
}

class PaymentRepositoryImpl(private val paymentDataSource: PaymentDataSource)
    : PaymentRepository {


    override fun checkout(paymentRequest: PaymentRequest,
                          onSuccess: ((PaymentTransaction) -> Unit)?,
                          onError: ((error: String) -> Unit)?) {

        paymentDataSource
                .checkout(paymentRequest)
                .addThreads()
                .subscribe({
                    onSuccess?.invoke(it)
                }, {
                    onError?.invoke(it.parser.error)
                })
    }

}
