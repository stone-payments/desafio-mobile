package douglasspgyn.com.github.desafiostone.business.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Douglas on 12/11/17.
 */

class RequestManager {

    companion object {
        fun <T> newRequest(originalCall: Call<T>, callback: RequestCallback<T>) {
            originalCall.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>?, response: Response<T>?) {
                    if (response != null && response.isSuccessful) {
                        callback.onSuccess(response.body()!!)
                    } else {
                        callback.onError()
                    }
                }

                override fun onFailure(call: Call<T>?, t: Throwable?) {
                    callback.onError()
                }
            })
        }
    }
}

interface RequestCallback<in T> {
    fun onSuccess(t: T)
    fun onError()
}