/*
 * copyright © ZERONE MICROSYSTEMS PRIVATE LIMITED. All rights reserved.
 */

package com.zup.cash.ui.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zup.cash.R
import com.zup.cash.utils.common.Resource
import com.zup.cash.utils.rx.SchedulerProvider
import com.zup.merchant.utils.network.NetworkHelper
import io.reactivex.disposables.CompositeDisposable
import javax.net.ssl.HttpsURLConnection

abstract class BaseViewModel(
    protected val schedulerProvider: SchedulerProvider,
    protected val compositeDisposable: CompositeDisposable,
    protected val networkHelper: NetworkHelper
) : ViewModel() {

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    val messageStringId: MutableLiveData<Resource<Int>> = MutableLiveData()

    val messageString: MutableLiveData<Resource<String>> = MutableLiveData()

    protected fun checkInternetConnection(): Boolean =
        if (networkHelper.isNetworkConnected()) true
        else {
            messageStringId.postValue(Resource.error(R.string.network_connection_error))
            false
        }

    protected fun handleNetworkError(err: Throwable?) =
        err?.let {
            networkHelper.castToNetworkError(it).run {
                when (status) {
                    Log.e("Status code",status.toString())
                            -1 -> messageStringId.postValue(Resource.error(R.string.network_default_error))
                    0 -> messageStringId.postValue(Resource.error(R.string.server_connection_error))
                    HttpsURLConnection.HTTP_PRECON_FAILED -> {
//                        paymentFailed()
                    }
                    HttpsURLConnection.HTTP_UNAUTHORIZED -> {
//                        forcedLogoutUser()
                        messageStringId.postValue(Resource.error(R.string.server_connection_error))
                    }
                    HttpsURLConnection.HTTP_INTERNAL_ERROR ->
                        messageString.postValue(Resource.error(message))
                    HttpsURLConnection.HTTP_UNAVAILABLE ->
                        messageStringId.postValue(Resource.error(R.string.network_server_not_available))
                    else -> messageString.postValue(Resource.error(message))
                }
            }
        }

    abstract fun onCreate()
}