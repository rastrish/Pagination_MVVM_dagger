package com.zup.cash.ui.main

import androidx.lifecycle.MutableLiveData
import com.zup.cash.ZupRepository
import com.zup.cash.ui.base.BaseViewModel
import com.zup.cash.utils.rx.SchedulerProvider
import com.zup.merchant.utils.network.NetworkHelper
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(schedulerProvider: SchedulerProvider,
                    compositeDisposable: CompositeDisposable,
                    networkHelper: NetworkHelper,
                    private val zupRepository : ZupRepository):
    BaseViewModel(schedulerProvider,compositeDisposable,networkHelper) {

    val phoneField : MutableLiveData<String> = MutableLiveData()
    val loggingIn: MutableLiveData<Boolean> = MutableLiveData()

    override fun onCreate() {}

    fun onPhoneChange(phoneNumber: String) = phoneField.postValue(phoneNumber)

    fun login(){
        val phoneNumber = phoneField.value
        //TODO check null values and validations for phone number
        if(checkInternetConnection()){
            loggingIn.postValue(true) //show progress bar
            compositeDisposable.addAll(
                zupRepository.login(phoneNumber).
                    subscribeOn(schedulerProvider.io()).
                    subscribe({
                        loggingIn.postValue(false)//hide progress bar
                    },{
                        loggingIn.postValue(false)//hide progress bar
                    })
            )
        }
    }
}