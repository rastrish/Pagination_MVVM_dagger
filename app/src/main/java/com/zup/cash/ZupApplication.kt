package com.zup.cash

import android.app.Application
import com.zup.cash.di.component.ApplicationComponent
import com.zup.cash.di.component.DaggerApplicationComponent
import com.zup.cash.di.module.ApplicationModule

class ZupApplication:Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }

    fun setComponent(applicationComponent: ApplicationComponent) {
        this.applicationComponent = applicationComponent
    }
}