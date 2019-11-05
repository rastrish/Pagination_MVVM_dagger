package com.zup.cash.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.zup.cash.ZupApplication
import com.zup.cash.data.Networking
import com.zup.cash.data.ZupNetworkService
import com.zup.cash.di.ApplicationContext
import com.zup.cash.utils.rx.RxSchedulerProvider
import com.zup.cash.utils.rx.SchedulerProvider
import com.zup.merchant.utils.encryption.AESCiper
import com.zup.merchant.utils.encryption.RSACiper
import com.zup.merchant.utils.network.NetworkHelper
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: ZupApplication) {

    @Provides
    @Singleton
    fun provideApplication():Application = application

    @Singleton
    @Provides
    @ApplicationContext
    fun provideContext(): Context = application

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences =
        application.getSharedPreferences("zup-shared-prefs", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideNetworkHelper(): NetworkHelper = NetworkHelper(application)

    @Provides
    @Singleton
    fun provideZupNetworkService():ZupNetworkService = Networking.createZupNetworkService()


    @Provides
    @Singleton
    fun provideAesCiper() : AESCiper = AESCiper()

    @Provides
    @Singleton
    fun provideRsaCiper() : RSACiper = RSACiper()
}