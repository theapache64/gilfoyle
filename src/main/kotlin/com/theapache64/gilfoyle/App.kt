package com.theapache64.gilfoyle

import com.theapache64.cyclone.core.Application
import com.theapache64.gilfoyle.di.components.DaggerGilfoyleComponent
import com.theapache64.gilfoyle.feature.splash.SplashActivity

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DaggerGilfoyleComponent.builder()
            .build()
            .inject(this)

        startActivity(SplashActivity.getStartIntent())
    }
}


fun main(args : Array<String>) {
    App().onCreate()
}