package com.zup.cash.ui.splash

import com.zup.cash.ZupRepository
import com.zup.cash.ui.base.BaseViewModel
import com.zup.cash.utils.rx.SchedulerProvider
import com.zup.merchant.utils.network.NetworkHelper
import io.reactivex.disposables.CompositeDisposable

class SplashViewModel(schedulerProvider: SchedulerProvider,
                      compositeDisposable: CompositeDisposable,
                      networkHelper: NetworkHelper, val zupRepository: ZupRepository)
    : BaseViewModel(schedulerProvider,compositeDisposable,networkHelper) {
    override fun onCreate() {

    }

}