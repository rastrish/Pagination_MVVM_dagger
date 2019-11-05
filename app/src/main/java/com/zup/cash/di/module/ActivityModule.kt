package com.zup.cash.di.module

import androidx.lifecycle.ViewModelProviders
import com.zup.cash.ZupRepository
import com.zup.cash.ui.base.BaseActivity
import com.zup.cash.ui.khata.KhataViewModel
import com.zup.cash.ui.main.MainViewModel
import com.zup.cash.ui.splash.SplashViewModel
import com.zup.cash.utils.ViewModelProviderFactory
import com.zup.cash.utils.rx.SchedulerProvider
import com.zup.merchant.utils.encryption.AESCiper
import com.zup.merchant.utils.encryption.RSACiper
import com.zup.merchant.utils.network.NetworkHelper
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule(private val activity: BaseActivity<*>) {

    @Provides
    fun provideSplashViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        zupRepository: ZupRepository
    ): SplashViewModel = ViewModelProviders.of(
        activity, ViewModelProviderFactory(SplashViewModel::class) {
            SplashViewModel(schedulerProvider, compositeDisposable, networkHelper, zupRepository)
        }).get(SplashViewModel::class.java)

    @Provides
    fun provideMainViewModel(
        schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper, zupRepository: ZupRepository
    ): MainViewModel =
        ViewModelProviders.of(activity,
            ViewModelProviderFactory(MainViewModel::class) {
                MainViewModel(
                    schedulerProvider, compositeDisposable,
                    networkHelper, zupRepository
                )
            }).get(MainViewModel::class.java)

    @Provides
    fun provideKhataViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        aesCiper: AESCiper,
        rsaCiper: RSACiper,
        zupRepository: ZupRepository
    ): KhataViewModel =
        ViewModelProviders.of(activity,
            ViewModelProviderFactory(KhataViewModel::class) {
                KhataViewModel(
                    schedulerProvider, compositeDisposable,
                    networkHelper, aesCiper, rsaCiper, zupRepository
                )
            }).get(KhataViewModel::class.java)
}