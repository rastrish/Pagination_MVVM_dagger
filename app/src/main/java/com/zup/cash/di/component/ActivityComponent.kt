package com.zup.cash.di.component

import com.zup.cash.di.ActivityScope
import com.zup.cash.di.module.ActivityModule
import com.zup.cash.ui.khata.KhataActivity
import com.zup.cash.ui.khata.KhataViewModel
import com.zup.cash.ui.main.MainActivity
import com.zup.cash.ui.splash.SplashScreenActivty
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class],modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(splashScreenActivty: SplashScreenActivty)

    fun inject(khataActivity: KhataActivity)
}